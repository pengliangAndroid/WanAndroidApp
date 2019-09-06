package com.funny.wanandroid.common

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.funny.appframework.base.BaseActivity
import com.funny.appframework.base.gone
import com.funny.appframework.base.visible
import com.funny.appframework.utils.ColorUtil
import com.funny.appframework.utils.LogUtil
import com.funny.appframework.utils.SPUtils
import com.funny.wanandroid.AppConstants
import com.funny.wanandroid.R
import com.funny.wanandroid.widget.FixInsetsFrameLayout

/**
 * @author pengl
 */

abstract class BaseAppActivity : BaseActivity(){
    protected var flToolbar: ViewGroup? = null

    var pToolbar: Toolbar? = null

    var night: Boolean = false

    protected var rootLayout: ViewGroup? = null

    protected var toolbarId: Int = 0

    protected var useCustomToolbar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.i("BaseAppActivity onCreate")
        val nightMode = SPUtils.get(this,AppConstants.KEY_NIGHT_MODE,false) as Boolean
        night = nightMode
        val theme = if(nightMode) R.style.BaseAppThemeDark else R.style.BaseAppThemeLight
        setTheme(theme)

        super.onCreate(savedInstanceState)
    }

    /**
     * 设置Act顶层布局
     */
    override fun setupContentView(){
        super.setContentView(R.layout.activity_base)
        rootLayout = findViewById(R.id.root_layout)
        LogUtil.i("BaseAppActivity shouldSetupContentView")
        setStatusBarColor(ColorUtil.getAttrColor(this,R.attr.colorPrimary))

        initNightLayer()
        initToolbar()

        if(!setupCustomToolbar())
            setContentView(getLayoutId())
    }

    open fun setupCustomToolbar() : Boolean{
        return false
    }

    override fun setContentView(layoutId: Int) {
        val view = View.inflate(this, layoutId, null)
        setContentView(view)
    }

    protected fun setContentView(layoutId: Int,useCustomToolbar: Boolean,toolbarId: Int){
        this.toolbarId = toolbarId
        this.useCustomToolbar = useCustomToolbar
        setContentView(layoutId)
    }

    override fun setContentView(view: View?) {
        if(useCustomToolbar){
            rootLayout?.removeView(flToolbar)

            val rootView = findViewById<FixInsetsFrameLayout>(R.id.base_container)
            rootView?.addView(view,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))

            if (toolbarId <= 0)
                return

            initToolbar(toolbarId)
        }else{
            val rootView = findViewById<FixInsetsFrameLayout>(R.id.base_container)
            rootView?.addView(view,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
            initToolbar()
        }
    }

    protected fun setFullScreen() {
        removeDefaultToolbar()
        val localFixInsetsFrameLayout = findViewById<FixInsetsFrameLayout>(R.id.base_container)
        localFixInsetsFrameLayout?.setInsetEnable(true)
    }

    override fun onResume() {
        super.onResume()
        pToolbar?.setNavigationIcon(getToolbarHomeImgRes())
    }

    protected open fun getToolbarHomeImgRes() : Int{
        return R.mipmap.toolbar_back_white
    }

    protected fun removeDefaultToolbar(){
        rootLayout?.removeView(flToolbar)
    }

    private fun initNightLayer(){
        if(night) showNightLayer()
        else hideNightLayer()
    }

    private fun initToolbar(){
        pToolbar = findViewById(R.id.toolbar)
        flToolbar = findViewById(R.id.fl_toolbar)

        pToolbar?.let{
           setSupportActionBar(it)
           supportActionBar?.setDisplayHomeAsUpEnabled(true)

           it.setBackgroundColor(ColorUtil.getAttrColor(this,R.attr.colorPrimary))
           it.setTitleTextColor(ColorUtil.getAttrColor(this,R.attr.themeToolbarTextColor))
        }
    }

    private fun initToolbar(toolbarId : Int){
        pToolbar = findViewById(toolbarId)
        pToolbar?.let{
            setSupportActionBar(it)
        }
    }

    private fun hideNightLayer() {
        val view = findViewById<View>(R.id.night_layer)
        view?.gone()
    }

    private fun showNightLayer() {
        val view = findViewById<View>(R.id.night_layer)
        view?.visible()
    }

    private fun hideToolbar(){
        pToolbar?.gone()
        flToolbar?.gone()
    }


}