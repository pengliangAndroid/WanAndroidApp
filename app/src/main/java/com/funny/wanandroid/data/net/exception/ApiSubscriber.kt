package com.funny.appframework.data.net.exception


import android.content.Context
import com.funny.appframework.data.net.resp.BaseResp
import com.funny.appframework.utils.LogUtil
import io.reactivex.Observer

/**
 * Subscriber基类,可以在这里处理client网络连接状况
 * （比如没有，没有4g，没有联网，加载框处理等）
 * @author pengl
 */
abstract class ApiSubscriber<T, R> : Observer<T> {

    private var context: Context ?= null

    constructor() {}

    constructor(context: Context) {
        this.context = context
    }

    override fun onError(e: Throwable) {
        LogUtil.d("ApiSubscriber.throwable =$e")
        LogUtil.d("ApiSubscriber.throwable =" + e.message)

        if (e is Exception) {
            //访问获得对应的Exception
            onFail(ExceptionHandle.handleException(e))
        } else {
            //将Throwable 和 未知错误的status code返回
            onFail(ApiException(e, ExceptionHandle.ERROR.UNKNOWN))
        }

    }

    override fun onNext(t: T) {
        val baseResp = t as BaseResp<*>
        val meta = baseResp.meta

        if (meta == null || meta.code == 0) {
            val model = baseResp.data as R
            onSuccess(model)
        } else {
            onFail(ApiException(meta.message,meta.code))
        }
    }


    override fun onComplete() {
        //LogUtil.d("ApiSubscriber.onCompleted()")
    }

//    override fun onSubscribe(d: Disposable) {
//        //LogUtil.d("ApiSubscriber.onSubscribe()")
//    }

    /**
     * 错误回调
     */
    protected abstract fun onFail(ex: ApiException)

    /**
     * 成功回调
     * @param data
     */
    abstract fun onSuccess(data: R)

}