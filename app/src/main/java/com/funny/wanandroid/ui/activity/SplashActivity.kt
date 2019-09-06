package com.funny.wanandroid.ui.activity

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.funny.appframework.base.BaseActivity
import com.funny.appframework.utils.DeviceUtils
import com.funny.wanandroid.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViews(savedState: Bundle?) {
        val tvVersionName = tv_version_name
        tvVersionName.text = "v${DeviceUtils.getVersionName(this)}"

        val alphaAnimation = AlphaAnimation(0.2f,1.0f)
        alphaAnimation.duration = 2000
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                startActivity<MainActivity>()
                finish()

            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })

        val ivWebIcon = iv_web_icon
        ivWebIcon.startAnimation(alphaAnimation)
    }

    override fun initData() {
//        checkPermission()
    }

//    private fun checkPermission(){
//        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)
//        EasyPermissions.requestPermissions(this,"应用需要以下权限，请允许",0,*permissions)
//    }
//
//    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
//        //super.onPermissionsGranted(requestCode, perms)
//        if(requestCode != 0) return
//
//        if(perms.isNullOrEmpty()) return
//
//        if(perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
//            perms.contains(Manifest.permission.READ_PHONE_STATE)){
//            val deviceId = DeviceUtils.getDeviceId(this)
//            LogUtil.i("deviceId:$deviceId")
//            iv_web_icon.startAnimation(alphaAnimation)
//        }
//    }
}
