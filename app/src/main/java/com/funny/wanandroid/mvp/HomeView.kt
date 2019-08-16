package com.funny.appframework.mvp

import com.funny.appframework.base.mvp.MvpView

/**
 * @author pengl
 */
interface HomeView : MvpView{
    fun onGetDataSuccess(data: List<String>)

    fun onGetDataFail(error: String)
}