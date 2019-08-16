package com.funny.appframework.base

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author pengl
 */

fun Fragment.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) : Toast{
    val toast = Toast.makeText(activity?.applicationContext,msg,duration)
    toast.show()
    return toast
}

fun Fragment.showToast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) : Toast{
    val msg = activity?.applicationContext?.resources?.getString(msgId) ?: ""
    return showToast(msg,duration)
}


fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) : Toast{
    val toast = Toast.makeText(this,msg,duration)
    toast.show()
    return toast
}

fun Context.showToast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) : Toast{
    val msg = getString(msgId)
    return showToast(msg,duration)
}

fun Context.getResString(msgId : Int, vararg formatArgs: Any) : String{
    return resources.getString(msgId,formatArgs)
}

fun Fragment.getResString(msgId : Int, vararg formatArgs: Any) : String{
    return resources.getString(msgId,formatArgs)
}


fun View.dp2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dp(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

