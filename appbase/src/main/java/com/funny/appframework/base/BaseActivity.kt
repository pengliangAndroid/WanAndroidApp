package com.funny.appframework.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.funny.appframework.utils.StatusBarCompat
import com.funny.appframework.utils.StatusBarUtil
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions





/**
 * @author pengl
 */

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    var isStatusCompat = true
    var statusBarColor = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        BaseActivityManager.instance.addActivity(this)

        StatusBarUtil.StatusBarDarkMode(this)

        compatStatusBar()

        initViews(savedInstanceState)
        initData()
    }

    abstract fun getLayoutId() : Int

    abstract fun initViews(savedState : Bundle?)

    abstract fun initData()

    private fun compatStatusBar() {
        if (isStatusCompat) {
            if (statusBarColor == -1) {
                val resourceId = resources.getIdentifier("colorPrimary", "color", packageName)
                statusBarColor = resources.getColor(resourceId)
            }

            StatusBarCompat.compat(this, statusBarColor)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseActivityManager.instance.removeActivity(this)
        closeKeyboard()
    }

    fun closeKeyboard(){
        val decorView = window.peekDecorView()
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(decorView.windowToken,0);
    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("BaseActivity", "onPermissionsGranted : $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuilder()

        perms.forEach { sb.append(it).append("\n") }

        sb.replace(sb.length - 2, sb.length, "")

        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            showToast(R.string.msg_permission_refuse_text)

            AppSettingsDialog.Builder(this)
                .setRationale(getResString(com.funny.app.base.R.string.msg_permission_rationale_text,sb.toString()))
                .setPositiveButton(getResString(com.funny.app.base.R.string.msg_permission_positive_text))
                .setNegativeButton(getResString(com.funny.app.base.R.string.msg_permission_negative_text))
                .build()
                .show()
        }
    }

}