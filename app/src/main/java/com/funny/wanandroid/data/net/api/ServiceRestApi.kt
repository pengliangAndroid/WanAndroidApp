package com.funny.appframework.data.net.api

import com.funny.appframework.data.model.ArticleInfo
import com.funny.appframework.data.model.PageBean
import com.funny.appframework.data.model.ProjectInfo
import com.funny.appframework.data.net.resp.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author pengl
 */

interface ServiceRestApi{


    @GET("article/listproject/{pageNum}/json")
    fun getHomeProjectList(@Path("pageNum") pageNum : Int) : Observable<HttpResult<PageBean<ProjectInfo>>>


    @GET("article/article/list/{pageNum}/json")
    fun getHomeArticleList(@Path("pageNum") pageNum : Int) : Observable<HttpResult<PageBean<ArticleInfo>>>
}