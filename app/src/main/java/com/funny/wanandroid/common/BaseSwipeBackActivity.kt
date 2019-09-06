package com.funny.wanandroid.common

import android.os.Build
import android.os.Bundle
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper

/**
 * @author pengl
 */

abstract class BaseSwipeBackActivity : BaseAppActivity(), BGASwipeBackHelper.Delegate{
    public lateinit var swipeBackHelper : BGASwipeBackHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        initSwipeBackFinish()
        super.onCreate(savedInstanceState)
    }

    private fun initSwipeBackFinish(){
        swipeBackHelper = BGASwipeBackHelper(this, this)

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
//        swipeBackHelper.setSwipeBackEnable(true)
//        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
//        swipeBackHelper.setIsOnlyTrackingLeftEdge(true)
//        // 设置是否是微信滑动返回样式。默认值为 true
//        swipeBackHelper.setIsWeChatStyle(true)
//        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
//        swipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
//        // 设置是否显示滑动返回的阴影效果。默认值为 true
//        swipeBackHelper.setIsNeedShowShadow(true)
//        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
//        swipeBackHelper.setIsShadowAlphaGradient(true)
//        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
//        swipeBackHelper.setSwipeBackThreshold(0.3f)
//        // 设置底部导航条是否悬浮在内容上，默认值为 false
//        swipeBackHelper.setIsNavigationBarOverlap(false)
    }



    override fun onSwipeBackLayoutExecuted() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            swipeBackHelper.swipeBackward()
        }else{
            finish()
            overridePendingTransition(0,0)
        }
    }

    override fun onSwipeBackLayoutSlide(slideOffset: Float) {

    }

    override fun onSwipeBackLayoutCancel() {

    }

    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val isSlide = swipeBackHelper.isSliding as Boolean
        if(isSlide)
            return

        swipeBackHelper.swipeBackward()
    }
}