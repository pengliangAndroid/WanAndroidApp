package com.funny.appframework.data.net.resp

/**
 * @author pengl
 */
data class BaseResp<T>(val data : T,val meta : Meta?)
data class Meta(val code : Int,val message : String)
