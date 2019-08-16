package com.funny.appframework.base.mvp

/**
 * @author pengl
 */
interface Presenter<in T : MvpView> {
    fun attachView(view: T)

    fun detachView()
}
