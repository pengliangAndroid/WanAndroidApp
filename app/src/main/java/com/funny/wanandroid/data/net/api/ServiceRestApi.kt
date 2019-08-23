package com.funny.appframework.data.net.api

import com.funny.appframework.data.model.*
import com.funny.appframework.data.net.resp.HttpResult
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @author pengl
 */

interface ServiceRestApi{


    @GET("article/listproject/{pageNum}/json")
    fun getHomeProjectList(@Path("pageNum") pageNum : Int) : Observable<HttpResult<PageBean<ProjectInfo>>>



    @GET("project/list/{pageNum}/json")
    fun getProjectListByCid(@Path("pageNum") pageNum : Int,@Query("cid") cid : Int) : Observable<HttpResult<PageBean<ProjectInfo>>>


    @GET("article/list/{pageNum}/json")
    fun getHomeArticleList(@Path("pageNum") pageNum : String) : Observable<HttpResult<PageBean<ArticleInfo>>>

    @GET("article/list/{pageNum}/json")
    fun getArticleListByCid(@Path("pageNum") pageNum : Int,@Query("cid") cid : Int) : Observable<HttpResult<PageBean<ArticleInfo>>>


    @GET("article/top/json")
    fun getTopArticleList() : Observable<HttpResult<List<ArticleInfo>>>


    /**
     * 获取首页Banner
     */
    @GET("banner/json")
    fun getHomeBannerList() : Observable<HttpResult<List<BannerInfo>>>

    @GET("friend/json")
    fun getFriendLinkList() : Observable<HttpResult<List<FriendLinkInfo>>>

    @GET("hotkey/json")
    fun getHotSearchKeyList() : Observable<HttpResult<List<HotSearchKey>>>


    @GET("tree/json")
    fun getSystemNodeTree() : Observable<HttpResult<List<SystemNodeInfo>>>

    @GET("navi/json")
    fun getNavList() : Observable<HttpResult<List<NavInfo>>>

    //username，password
    @POST("user/login")
    fun login(@Body body: RequestBody) : Observable<HttpResult<Any>>

    @POST("user/logout")
    fun logout(@Body body: RequestBody) : Observable<HttpResult<Any>>

    //username,password,repassword
    @POST("user/register")
    fun register(@Body body: RequestBody) : Observable<HttpResult<Any>>

    @GET("lg/collect/list/{pageNum}/json")
    fun getCollectArticleList(@Path("pageNum") pageNum: Int) : Observable<HttpResult<PageBean<ArticleInfo>>>

    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: String) : Observable<HttpResult<Any>>

    //title，author，link
    @POST("lg/collect/add/json")
    fun collectWebArticle(@Body body: RequestBody) : Observable<HttpResult<Any>>

    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: String) : Observable<HttpResult<Any>>

    @POST("lg/uncollect/{id}/json")
    fun cancelCollectArticleInMine(@Path("id") id: String) : Observable<HttpResult<Any>>

    @GET("lg/collect/usertools/json")
    fun getCollectLinkList() : Observable<HttpResult<PageBean<FriendLinkInfo>>>

    //name,link
    @POST("lg/collect/addtool/json")
    fun collectLink(@Body body: RequestBody) : Observable<HttpResult<Any>>

    //id,name,link
    @POST("lg/collect/updatetool/json")
    fun updateCollectLink(@Body body: RequestBody) : Observable<HttpResult<Any>>

    //id
    @POST("lg/collect/deletetool/json")
    fun deleteCollectLink(@Body body: RequestBody) : Observable<HttpResult<Any>>

    @GET("article/query/{pageNum]/json")
    fun searchArticle(@Path("pageNum") pageNum: Int,@Query("k") key : String) : Observable<HttpResult<PageBean<ArticleInfo>>>
}