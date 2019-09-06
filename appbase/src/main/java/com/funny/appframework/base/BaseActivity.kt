package com.funny.appframework.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.funny.app.base.R
import com.funny.appframework.utils.LogUtil
import com.jaeger.library.StatusBarUtil
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * @author pengl
 */

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i("BaseActivity onCreate")
        BaseActivityManager.instance.addActivity(this)

        setupContentView()

        initViews(savedInstanceState)

        initData()
    }

    open protected fun setupContentView() {
        LogUtil.i("BaseActivity defaultSetupContentView")
        setContentView(getLayoutId())
    }

    abstract fun getLayoutId() : Int

    abstract fun initViews(savedState : Bundle?)

    abstract fun initData()

    /**
     * 设置状态栏背景颜色
     */
    fun setStatusBarColor(@ColorInt color: Int){
        StatusBarUtil.setColor(this,color,0)
    }


    fun setStatusBarColorForSwipeBack(@ColorInt color: Int) {
        StatusBarUtil.setColorForSwipeBack(this, color, 0)
    }

    /**
     * 设置状态栏背景是否亮色，若亮色则状态栏字体，图标是黑色
     */
    fun setStatusBarLightMode(lightMode: Boolean){
        if(lightMode) StatusBarUtil.setDarkMode(this)
        else StatusBarUtil.setLightMode(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseActivityManager.instance.removeActivity(this)
        closeKeyboard()
    }

    protected fun closeKeyboard(){
        val focusView = currentFocus
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(focusView?.applicationWindowToken,0)
    }

    protected fun showKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(2, 1)
    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        LogUtil.i("BaseActivity", "onPermissionsGranted : $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuilder()

        perms.forEach { sb.append(it).append("\n") }

        sb.replace(sb.length - 2, sb.length, "")

        //用户点击拒绝并不在询问时候调用
        val isDenied = EasyPermissions.somePermissionPermanentlyDenied(this, perms)
        isDenied.let {
//            showToast(R.string.msg_permission_refuse_text)

            AppSettingsDialog.Builder(this)
                .setRationale(getResString(R.string.msg_permission_rationale_text,sb.toString()))
                .setPositiveButton(getResString(R.string.msg_permission_positive_text))
                .setNegativeButton(getResString(R.string.msg_permission_negative_text))
                .build()
                .show()
        }
    }

}