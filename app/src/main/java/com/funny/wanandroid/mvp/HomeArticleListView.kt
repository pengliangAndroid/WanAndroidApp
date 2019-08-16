package com.funny.appframework.mvp

import com.funny.appframework.base.mvp.MvpView
import com.funny.appframework.data.model.ArticleInfo

/**
 * @author pengl
 */
interface HomeArticleListView : MvpView{
    fun onGetDataSuccess(data: List<ArticleInfo>)

    fun onGetDataFail(error: String)
}