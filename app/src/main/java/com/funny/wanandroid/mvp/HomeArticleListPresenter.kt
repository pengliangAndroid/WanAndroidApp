package com.funny.appframework.mvp

import com.funny.appframework.base.mvp.BasePresenter
import com.funny.appframework.data.DataManager
import com.funny.appframework.data.model.ArticleInfo
import com.funny.appframework.data.model.PageBean
import com.funny.appframework.data.net.exception.ApiException
import com.funny.appframework.data.net.exception.ApiSubscriber
import com.funny.appframework.data.net.resp.HttpResult
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.disposables.Disposable

/**
 * @author pengl
 */
class HomeArticleListPresenter : BasePresenter<HomeArticleListView>(){
    private var curPage = 0

    private var hasMoreData = true

    fun getHomeArticleList(){
        DataManager.get().getHomeArticleList(curPage)
            .compose(SchedulerUtils.ioToMain())
            .subscribe(object : ApiSubscriber<HttpResult<PageBean<ArticleInfo>>, PageBean<ArticleInfo>>(){
                override fun onSuccess(data: PageBean<ArticleInfo>) {
                    if(data.pageCount == curPage)
                        hasMoreData = false
                    curPage++
                    mvpView?.onGetDataSuccess(data.datas)
                }

                override fun onSubscribe(d: Disposable) {
                    addSubscription(d)
                }

                override fun onFail(ex: ApiException) {
                    mvpView?.onGetDataFail(ex.message)
                }
            })
    }

    fun setCurPage(curPage: Int) {
        hasMoreData = true
        this.curPage = curPage
    }

}