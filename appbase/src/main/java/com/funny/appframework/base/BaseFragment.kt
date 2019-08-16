package com.funny.appframework.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.funny.app.base.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import kotlin.properties.Delegates



/**
 * @author pengl
 */

abstract class BaseFragment : Fragment(), EasyPermissions.PermissionCallbacks{
    var baseActivity : BaseActivity?= null

    var rootView : View by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(getLayoutId(),null)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(rootView,savedInstanceState)
        initData()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        baseActivity = context as BaseActivity
    }

    override fun onDetach() {
        super.onDetach()

        baseActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (rootView.parent != null) {
            val parent = this.rootView.parent as ViewGroup
            parent.removeView(this.rootView)
        }
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
                .setRationale(getResString(R.string.msg_permission_rationale_text,sb.toString()))
                .setPositiveButton(getResString(R.string.msg_permission_positive_text))
                .setNegativeButton(getResString(R.string.msg_permission_negative_text))
                .build()
                .show()
        }
    }

    abstract fun getLayoutId() : Int

    abstract fun initViews(rootView : View, savedState : Bundle?)

    abstract fun initData()


}