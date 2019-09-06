package com.funny.wanandroid.ui.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.daimajia.numberprogressbar.NumberProgressBar
import com.funny.appframework.base.gone
import com.funny.appframework.data.model.ArticleInfo
import com.funny.wanandroid.R
import com.funny.wanandroid.common.BaseSwipeBackActivity
import kotlinx.android.synthetic.main.activity_article_detail.*



class ArticleDetailActivity : BaseSwipeBackActivity() {

    var articleInfo: ArticleInfo? = null

    var pageProgressBar : NumberProgressBar? = null
    var topLayout : ViewGroup? = null


    companion object {
        const val EXTRA_ENTRY_ID = "extra_entry_id"
        const val EXTRA_ENTRY_BEAN = "extra_entry_bean"
    }

    override fun setupCustomToolbar(): Boolean {
        val colorId = if(night) R.color.color212731 else R.color.status_bar_black
        setStatusBarColorForSwipeBack(resources.getColor(colorId))

        setContentView(getLayoutId(), true, R.id.toolbar)
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_article_detail
    }

    override fun initViews(savedState: Bundle?) {
        articleInfo = intent.getParcelableExtra(EXTRA_ENTRY_BEAN)

        pageProgressBar = progressbar
        topLayout = top
        pageProgressBar?.setProgressTextVisibility(NumberProgressBar.ProgressTextVisibility.Invisible)

        supportActionBar?.title = articleInfo?.title
        initWebView()
    }

    private fun initWebView() {
        val webView = web_view
        webView.webChromeClient = WebChrome()
        webView.webViewClient = WebClient()

        val settings = webView.settings
        settings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = Context.MODE_PRIVATE
        }

        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }

    inner class WebClient : WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            pageProgressBar?.gone()
        }
    }

    inner class WebChrome : WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            pageProgressBar?.progress = newProgress
        }

    }

    override fun getToolbarHomeImgRes(): Int {
        return if(night) R.mipmap.toolbar_back_white else R.mipmap.toolbar_back_black
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuLayoutId = R.menu.menu_webview_light
        if(night){
            menuLayoutId = R.menu.menu_webview_dark
        }

        menuInflater.inflate(menuLayoutId,menu)
        return true
    }


    override fun initData() {
        articleInfo?.let {
            web_view.loadUrl(it.link)


        }
    }


}
