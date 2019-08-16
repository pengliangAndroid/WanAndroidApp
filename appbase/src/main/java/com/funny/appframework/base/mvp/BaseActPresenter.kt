package com.funny.appframework.base.mvp

import com.funny.appframework.base.BaseActivity

/**
 * @author pengl
 */
class BaseActPresenter<T : MvpView> : BasePresenter<T>() {
    protected var baseActivity: BaseActivity? = null

    override fun attachView(mvpView: T) {
        super.attachView(mvpView)

        baseActivity = mvpView as BaseActivity
    }

    override fun detachView() {
        super.detachView()

        baseActivity = null
    }


}
