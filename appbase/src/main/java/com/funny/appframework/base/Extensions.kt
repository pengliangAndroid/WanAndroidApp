package com.funny.appframework.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    val scale = resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.isVisible() : Boolean{
    return this.visibility == View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.createBitmap(): Bitmap{
    val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
    this.draw(Canvas(bitmap))
    return bitmap
}

fun View.closeInputMethod(){
    clearFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken,0)
}


//fun Any?.notNull(f: ()-> Unit){
//    if (this != null){
//        f()
//    }
//}


fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}

