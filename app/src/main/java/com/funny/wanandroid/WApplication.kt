package com.funny.wanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * @author pengl
 * */

class WApplication : Application(){

    private var refWatcher : RefWatcher? = null

    companion object{
        var context : Context by Delegates.notNull()
            private set

        fun getRefWatcher(context : Context) : RefWatcher? {
            val wApplication = context.applicationContext as WApplication
            return wApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        BGASwipeBackHelper.init(this, null)

        refWatcher = setupLeakCanary()

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                getRefWatcher(context)?.watch(p0)
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

        })
    }

    private fun setupLeakCanary() : RefWatcher{
        return if(LeakCanary.isInAnalyzerProcess(this))
            RefWatcher.DISABLED
        else LeakCanary.install(this)
    }
}