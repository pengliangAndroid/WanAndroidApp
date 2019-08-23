package com.funny.appframework.data.net.resp

/**
 * @author pengl
 */
data class HttpResult<R>(val errorCode:Int,val errorMsg:String,val data:R)
