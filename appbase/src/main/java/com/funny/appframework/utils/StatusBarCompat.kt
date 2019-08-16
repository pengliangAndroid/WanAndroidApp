package com.funny.appframework.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup

/**
 * 状态栏兼容处理类
 */
object StatusBarCompat {
    private val INVALID_VAL = -1
    private val COLOR_DEFAULT = Color.parseColor("#20000000")
    private val COLOR_TRANSPARENT = Color.parseColor("#00000000")

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun compat(activity: Activity, statusColor: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                //activity.getWindow().setStatusBarColor(COLOR_DEFAULT);

                activity.window.statusBarColor = statusColor

            }
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            var color = COLOR_DEFAULT
            val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
            if (statusColor != INVALID_VAL) {
                color = statusColor
            }
            val statusBarView = View(activity)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity)
            )
            statusBarView.setBackgroundColor(color)
            contentView.addView(statusBarView, lp)
        }

    }


    fun compat(activity: Activity) {
        compat(activity, COLOR_DEFAULT)
    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun fillStatusBar(activity: Activity, statusColor: Int) {
        val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        val statusBarView = View(activity)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.setBackgroundColor(statusColor)
        contentView.addView(statusBarView, lp)
    }
}