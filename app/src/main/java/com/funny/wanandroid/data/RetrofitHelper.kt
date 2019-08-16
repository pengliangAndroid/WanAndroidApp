package com.funny.appframework.data

import android.text.TextUtils
import androidx.annotation.NonNull
import com.funny.appframework.utils.LogUtil
import com.funny.appframework.utils.NetUtils
import com.funny.wanandroid.BuildConfig
import com.funny.wanandroid.WApplication.Companion.context
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates




/**
 * @author pengl
 */

object RetrofitHelper{
    private const val DEFAULT_TIMEOUT = 60L

    private var okHttpClient : OkHttpClient by Delegates.notNull()
    private var retrofit : Retrofit by Delegates.notNull()

    init {
        okHttpClient = buildOkHttpClient()
        retrofit = createRetrofit(okHttpClient)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        //custom OkHttp
        val builder = OkHttpClient.Builder()

        //timeout
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        //token header
        //builder.addInterceptor(HeaderInterceptor())
        //builder.addInterceptor(TokenInterceptor())

        //Log信息拦截器
        val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (BuildConfig.DEBUG) {
                LogUtil.i("HttpHelper", message)
            }
        })
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(logInterceptor)

        //cache
        val httpCacheDirectory = File(context.getCacheDir(), "OkHttpCache")
        builder.cache(Cache(httpCacheDirectory, 10 * 1024 * 1024))
        builder.addInterceptor(CacheControlInterceptor())

        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        val cookieJar = JavaNetCookieJar(cookieManager)
        builder.cookieJar(cookieJar)

        return builder.build()
    }

    fun <T> getService(tClass: Class<T>): T {
        return retrofit.create(tClass)
    }

    @NonNull
    fun createTempOkHttpClient(): OkHttpClient {
        //custom OkHttp
        val builder = OkHttpClient.Builder()

        //timeout
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        //Log信息拦截器
        val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (BuildConfig.DEBUG) {
                LogUtil.i("HttpHelper", message)
            }
        })
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logInterceptor)

        return builder.build()
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UrlConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun destroy() {
        okHttpClient.dispatcher().cancelAll()
    }

}

private class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        // Request customization: add request headers
        val requestBuilder = original.newBuilder()

        if (UrlConstants.accessToken != null) {
            val authorization = original.header("Authorization")
            if (TextUtils.isEmpty(authorization)) {
                requestBuilder.addHeader("Authorization", UrlConstants.accessToken)
            }
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

private class TokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return chain.proceed(request)
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private fun isTokenExpired(response: Response): Boolean {
        return response.code() == 401
    }

}


private class CacheControlInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val connected = NetUtils.isConnected(context)
        if (!connected) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }

        val response = chain.proceed(request)

        if (connected) {
            val maxAge = 60 * 60 // read from cache for 1 hour
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return response
    }
}
