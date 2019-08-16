package com.funny.appframework.base

import android.os.Bundle
import androidx.annotation.Nullable


/**
 * @author pengl
 */

abstract class BaseLazyFragment : BaseFragment(){
    //是否用户可见
    protected var isViewVisible = false

    //是否加载过数据
    protected var isLoadData = false

    private var isPrepared: Boolean = false


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (userVisibleHint) {
            isViewVisible = true
            //只有用户可见时进行加载
            lazyLoad()
        } else {
            isViewVisible = false
            onInVisible()
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        lazyLoad()
    }


    protected abstract fun loadData()

    fun onInVisible() {}

    protected fun lazyLoad() {
        if (!isPrepared || !isVisible || isLoadData) {
            return
        }

        loadData()
        isLoadData = true
    }

}