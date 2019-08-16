package com.funny.appframework.mvp

import com.funny.appframework.base.mvp.BasePresenter

/**
 * @author pengl
 */
class HomePresenter : BasePresenter<HomeView>(){

    fun getHomeData(){
//        DataManager.get().getNewsData()
//            .compose(SchedulerUtils.ioToMain())
//            .subscribe(object : ApiSubscriber<BaseResp<List<NewsTypeInfo>>,List<NewsTypeInfo>>(){
//                override fun onSuccess(data: List<NewsTypeInfo>) {
//                    mvpView?.onGetDataSuccess(data)
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
    }

}