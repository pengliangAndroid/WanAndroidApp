package com.funny.appframework.base.mvp

import com.funny.appframework.base.BaseActivity
import com.funny.appframework.base.BaseFragment

/**
 * @author pengl
 */
class BaseFrgPresenter<T : MvpView> : BasePresenter<T>() {
    var baseFragment: BaseFragment? = null

    var baseActivity: BaseActivity? = null


    override fun attachView(mvpView: T) {
        super.attachView(mvpView)

        baseFragment = mvpView as BaseFragment
        baseActivity = baseFragment?.baseActivity
    }

    override fun detachView() {
        super.detachView()

        baseFragment = null
        baseActivity = null
    }

}
