package com.funny.wanandroid.ui.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author pengl
 */
abstract class CommonAdapter<T>(layoutResId: Int, data: List<T>) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: T) {
        convertViewItem(holder, item)
    }

    abstract fun convertViewItem(holder: BaseViewHolder, item: T)
}
