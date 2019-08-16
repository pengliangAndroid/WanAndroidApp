package com.funny.appframework.data.net.exception

import android.util.Log
import retrofit2.adapter.rxjava2.HttpException

import java.net.ConnectException


/**
 * @author pengl
 */
object ExceptionHandle {

    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        Log.i("tag", "e.toString = $e")
        if (e is HttpException) {
            ex = ApiException(e, ERROR.HTTP_ERROR)
            when (e.code()) {
                NOT_FOUND, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex.message = "服务器异常，请稍后再试"
                UNAUTHORIZED, FORBIDDEN, REQUEST_TIMEOUT, INTERNAL_SERVER_ERROR, GATEWAY_TIMEOUT ->
                    //ex.code = httpException.code();
                    ex.message = "访问失败，网络错误"
                else -> ex.message = "访问失败，网络错误"
            }
            return ex
        } else if (e is ServerException) {
            ex = ApiException(e, e.code)
            ex.message = e.message
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(e, ERROR.NETWORK_ERROR)
            ex.message = "连接失败"
            return ex
        } else if (e is javax.net.ssl.SSLHandshakeException) {
            ex = ApiException(e, ERROR.SSL_ERROR)
            ex.message = "证书验证失败"
            return ex
        } else {
            ex = ApiException(e, ERROR.UNKNOWN)
            ex.message = "未知错误"
            return ex
        }
    }


    /**
     * 约定异常
     */
    object ERROR {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORK_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005
    }

}