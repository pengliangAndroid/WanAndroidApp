package com.funny.wanandroid.ui.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author pengl
 */
abstract class CommonAdapter<T>(layoutResId: Int, data: List<T>) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {

    override fun convert(baseViewHolder: BaseViewHolder, item: T) {
        convertViewItem(baseViewHolder, item)
    }

    abstract fun convertViewItem(baseViewHolder: BaseViewHolder, item: T)
}
