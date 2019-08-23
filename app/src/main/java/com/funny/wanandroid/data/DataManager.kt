package com.funny.appframework.data

import com.funny.appframework.data.model.ArticleInfo
import com.funny.appframework.data.model.PageBean
import com.funny.appframework.data.model.ProjectInfo
import com.funny.appframework.data.net.api.ServiceRestApi
import com.funny.appframework.data.net.resp.HttpResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody


/**
 * @author pengl
 */
class DataManager private constructor(){

    val retrofitHelper : RetrofitHelper by lazy { RetrofitHelper() }

    companion object{
        private const val JSON = "application/json;charset=utf-8"

        private val instance : DataManager by lazy { DataManager() }

        fun get() : DataManager = instance
    }

//    val retrofitHelper : RetrofitHelper = RetrofitHelper.

    private val gson : Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    fun getGson() = gson

    private fun createRequestBody(json: String): RequestBody {
        return RequestBody.create(MediaType.parse(JSON), json)
    }

    fun getHomeProjectList(pageNum : Int) : Observable<HttpResult<PageBean<ProjectInfo>>> {
        return retrofitHelper.getService(ServiceRestApi::class.java).getHomeProjectList(pageNum)
    }

    fun getHomeArticleList(pageNum : Int) : Observable<HttpResult<PageBean<ArticleInfo>>> {
        return retrofitHelper.getService(ServiceRestApi::class.java).getHomeArticleList("$pageNum")
    }

    fun getTopArticleList() : Observable<HttpResult<List<ArticleInfo>>> {

        return retrofitHelper.getService(ServiceRestApi::class.java).getTopArticleList()
    }

}