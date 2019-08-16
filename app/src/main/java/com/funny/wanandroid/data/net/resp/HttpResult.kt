package com.funny.appframework.data.net.resp

/**
 * @author pengl
 */
data class HttpResult<R>(val errorCode:String,val errorMsg:String,val data:R)
