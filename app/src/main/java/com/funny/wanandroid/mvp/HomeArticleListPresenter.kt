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
import io.reactivex.functions.BiFunction

/**
 * @author pengl
 */
class HomeArticleListPresenter : BasePresenter<HomeArticleListView>(){
    private var curPage = 0

    var hasMoreData = true

    fun getHomeArticleList(){
        val subscriber = object : ApiSubscriber<HttpResult<PageBean<ArticleInfo>>, PageBean<ArticleInfo>>(){
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
        }


        if(curPage == 0) {
            DataManager.get().getTopArticleList()
                .zipWith(DataManager.get().getHomeArticleList(curPage),
                    BiFunction<HttpResult<List<ArticleInfo>>, HttpResult<PageBean<ArticleInfo>>, HttpResult<PageBean<ArticleInfo>>> { t1, t2 ->
//                        val list = mutableListOf<ArticleInfo>()
//                        list.addAll(0,t1.data)
//                        list.addAll(t2.data.datas)
                        t1.data.forEach{it.isTop = true}
                        t2.data.datas.addAll(0,t1.data)
                        t2
                    })
                .compose(SchedulerUtils.ioToMain())
                .subscribe(subscriber)
        }else{
            DataManager.get().getHomeArticleList(curPage)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(subscriber)
        }
    }


//    private fun getTopArticleList(){
//        DataManager.get().getTopArticleList()
//            .compose(SchedulerUtils.ioToMain())
//            .subscribe(object : ApiSubscriber<HttpResult<List<ArticleInfo>>, List<ArticleInfo>>(){
//                override fun onSuccess(data: List<ArticleInfo>) {
//                    mvpView?.onGetTopDataSuccess(data)
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    addSubscription(d)
//                }
//
//                override fun onFail(ex: ApiException) {
//                    mvpView?.onGetDataFail(ex.message)
//                }
//            })
//    }


    fun setCurPage(curPage: Int) {
        hasMoreData = true
        this.curPage = curPage
    }

}