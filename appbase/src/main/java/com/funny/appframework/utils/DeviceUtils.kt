/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.funny.appframework.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.FileProvider
import com.funny.app.base.R
import java.io.File

object DeviceUtils {


    /**
     * SD卡判断
     *
     * @return boolean
     */
    val isSDCardAvailable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED


    /**
     * 获取手机品牌
     *
     * @return String
     */
    val phoneBrand: String
        get() = Build.BRAND


    /**
     * 获取手机型号
     *
     * @return String
     */
    val phoneModel: String
        get() = Build.MODEL


    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return int
     */
    val buildLevel: Int
        get() = Build.VERSION.SDK_INT


    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return String
     */
    val buildVersion: String
        get() = Build.VERSION.RELEASE


    /**
     * 获取当前App进程的id
     *
     * @return int
     */
    val appProcessId: Int
        get() = android.os.Process.myPid()

    /**
     * dp 转化为 px
     *
     * @param context context
     * @param dpValue dpValue
     * @return int
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    /**
     * px 转化为 dp
     *
     * @param context context
     * @param pxValue pxValue
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * sp 转化为 px
     *
     * @param context context
     * @param spValue spValue
     * @return int
     */
    fun p2px(context: Context, spValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }


    /**
     * px 转化为 sp
     *
     * @param context context
     * @param pxValue pxValue
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * 获取设备宽度（px）
     *
     * @param context context
     * @return int
     */
    fun deviceWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }


    /**
     * 获取设备高度（px）
     */
    fun deviceHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }


    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @param context context
     * @return String
     */
    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            val packageManager = context.packageManager
            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return versionName
    }


    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context context
     * @return String
     */
    fun getVersionCode(context: Context): String {
        var versionCode = ""
        try {
            val packageManager = context.packageManager
            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionCode = packInfo.versionCode.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return versionCode
    }


    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context context
     * @return String
     */
    fun getDeviceId(context: Context): String {
        val tm = context.getSystemService(
            Context.TELEPHONY_SERVICE
        ) as TelephonyManager
        val deviceId = tm.deviceId
        return deviceId ?: "-"
    }


    /**
     * 获取当前App进程的Name
     *
     * @param context context
     * @param processId processId
     * @return String
     */
    fun getAppProcessName(context: Context, processId: Int): String? {
        var processName: String? = null
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 获取所有运行App的进程集合
        val l = am.runningAppProcesses
        val i = l.iterator()
        val pm = context.packageManager
        while (i.hasNext()) {
            val info = i.next() as ActivityManager.RunningAppProcessInfo
            try {
                if (info.pid == processId) {
                    val c = pm.getApplicationLabel(
                        pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA)
                    )

                    processName = info.processName
                    return processName
                }
            } catch (e: Exception) {
                Log.e(DeviceUtils::class.java.name, e.message, e)
            }

        }
        return processName
    }


    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    fun isAppAlive(context: Context, packageName: String): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfos = activityManager.runningAppProcesses
        for (i in processInfos.indices) {
            if (processInfos[i].processName == packageName) {
                Log.i(
                    "NotificationLaunch",
                    String.format("the %s is running, isAppAlive return true", packageName)
                )
                return true
            }
        }
        Log.i(
            "NotificationLaunch",
            String.format("the %s is not running, isAppAlive return false", packageName)
        )
        return false
    }

    /**
     * Gps是否打开
     * @param context
     * @return
     */
    fun isGpsOpen(context: Context): Boolean {
        val lManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return if (!lManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            false
        } else {
            true
        }
    }


    /**
     * 创建App文件夹
     *
     * @param appName appName
     * @param application application
     * @param folderName folderName
     * @return String
     */
    @JvmOverloads
    fun createAPPFolder(appName: String, application: Application, folderName: String? = null): String {
        var root: File? = Environment.getExternalStorageDirectory()
        var folder: File
        /**
         * 如果存在SD卡
         */
        if (DeviceUtils.isSDCardAvailable && root != null) {
            folder = File(root, appName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = application.cacheDir
            folder = File(root, appName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
        }
        if (folderName != null) {
            folder = File(folder, folderName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
        }
        return folder.absolutePath
    }


    /**
     * 通过Uri找到File
     *
     * @param context context
     * @param uri uri
     * @return File
     */
    fun uri2File(context: Activity, uri: Uri): File {
        val file: File
        val project = arrayOf(MediaStore.Images.Media.DATA)
        val actualImageCursor = context.contentResolver
            .query(uri, project, null, null, null)
        if (actualImageCursor != null) {
            val actual_image_column_index = actualImageCursor.getColumnIndexOrThrow(
                MediaStore.Images.Media.DATA
            )
            actualImageCursor.moveToFirst()
            val img_path = actualImageCursor.getString(actual_image_column_index)
            file = File(img_path)
        } else {
            file = File(uri.path!!)
        }
        actualImageCursor?.close()
        return file
    }


    /**
     * 获取AndroidManifest.xml里 <meta-data>的值
     *
     * @param context context
     * @param name name
     * @return String
    </meta-data> */
    fun getMetaData(context: Context, name: String): String? {
        var value: String? = null
        try {
            val appInfo = context.packageManager
                .getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
            value = appInfo.metaData.getString(name)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return value
    }


    /**
     * 复制到剪贴板
     *
     * @param context context
     * @param content content
     */
    fun copy2Clipboard(context: Context, content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            val clipboardManager = context.getSystemService(
                Context.CLIPBOARD_SERVICE
            ) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText(
                context.getString(R.string.app_name),
                content
            )
            clipboardManager.setPrimaryClip(clipData)
        } else {
            val clipboardManager = context
                .getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
            clipboardManager.text = content
        }
    }


    /**
     * install APK File from folder
     * @param context
     * @param apkFullFileName  apk path+apk name
     */
    fun installApk(context: Context, apkFullFileName: String) {
        val apkFile = File(apkFullFileName)
        if (!apkFile.exists()) {
            return
        }
        val i = Intent(Intent.ACTION_VIEW)
        i.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)

    }

    /**
     * 应用分享图片
     * @param context
     * @param uri
     * @param title
     */
    fun shareImage(context: Context, uri: Uri, title: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/jpeg"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    /**
     * 应用分享
     * @param context
     * @param content
     */
    fun share(context: Context, content: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, content)
        context.startActivity(Intent.createChooser(intent, "分享"))
    }

    /**
     * 得到外部文件的URI
     * @param context
     * @param file
     * @return
     */
    fun getExternalFileUri(context: Context, file: File): Uri {
        val contentUri: Uri
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(context, context.packageName + ".fileProvider", file)
        } else {
            contentUri = Uri.fromFile(file)
        }

        return contentUri
    }

}
/**
 * 创建App文件夹
 *
 * @param appName appName
 * @param application application
 * @return String
 */
