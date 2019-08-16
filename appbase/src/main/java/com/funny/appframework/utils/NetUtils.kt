package com.funny.appframework.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo.State
import android.net.wifi.WifiManager
import android.provider.Settings
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

object NetUtils {

    val NETWORK_PHONE = 1
    val NO_NETWORK = 0
    val NETWORK_WIFI = 2

    /**
     * 手机网络下获取ip
     *
     * @return
     * @throws SocketException
     */
    val localIpAddress: String?
        @Throws(SocketException::class)
        get() {
            val en = NetworkInterface
                .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr
                        .hasMoreElements()
                ) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress().toString()
                    }
                }
            }
            return null
        }

    /**
     * 判断wifi是否可用
     *
     * @param inContext
     * @return true表示可用,false表示不可用
     */
    fun isWiFiActive(inContext: Context): Boolean {
        val context = inContext.applicationContext
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].typeName == "WIFI" && info[i].isConnected) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 判断当前网络是否能用，并判断网络类型 当前网络类型，0无网络，1手机网，2wifi网
     *
     * @param context
     */
    fun isNetworkActive(context: Context): Int {
        val wifiState: State?
        val mobileState: State?
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!
            .state
        // wifi和手机网同时开启时手机优先使用wifi网络
        return if (wifiState != null && mobileState != null
            && State.CONNECTED != wifiState
            && State.CONNECTED == mobileState
        ) {
            // 手机网络连接成功
            NETWORK_PHONE
        } else if (wifiState != null && mobileState != null
            && State.CONNECTED != wifiState
            && State.CONNECTED != mobileState
        ) {
            // 手机没有任何的网络
            NO_NETWORK
        } else {
            // 无线网络连接成功 (wifiState != null && State.CONNECTED == wifiState)
            NETWORK_WIFI
        }
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    fun isConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }

        return false
    }

    /**
     * 检测wifi是否可用
     *
     * @param context
     * @return
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if (wifiNetworkInfo!!.isConnected) {
            true
        } else false
    }

    /**
     * wifi下获取ip地址
     *
     * @param context
     * @return
     */
    fun getIP(context: Context): String? {
        val wifiManager = context
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            return null
        }

        val infor = wifiManager.connectionInfo
        val ipaddress = infor.ipAddress
        return intoIp(ipaddress)
    }

    /**
     * 转成16进制IP
     * @param ip
     * @return
     */
    fun intoIp(ip: Int): String {
        return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF)
                + "." + (ip shr 24 and 0xFF))
    }

    /**
     * 获取物理地址
     *
     * @return
     */
    fun getLocalMacAddress(context: Context): String {
        val wifi = context
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        return info.macAddress
    }


    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    /**
     * 打开网络设置界面
     */
    fun openSetting(activity: Activity) {
        /*Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);*/
        if (android.os.Build.VERSION.SDK_INT > 10) {
            //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
            activity.startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
        } else {
            activity.startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
        }
    }
}
