package com.funny.appframework.base

import android.app.Activity

/**
 * @author pengl
 */
class BaseActivityManager private constructor(){

    companion object{
        private val TAG = "BaseActivityManager"

        val instance : BaseActivityManager by lazy { BaseActivityManager() }

        val activities = mutableListOf<Activity>()
    }

    @Synchronized
    fun size() = activities.size

    @Synchronized
    fun getTopActivity() = if(size() > 0) activities[size() - 1] else null

    @Synchronized
    fun addActivity(activity: Activity){
        activities.add(activity)
    }

    @Synchronized
    fun removeActivity(activity: Activity){
//        if(activities.contains(activity))
        activities.remove(activity)
    }

    @Synchronized
    fun removeAllActivity(){
        for((index,act) in activities.withIndex()){
            activities.removeAt(index)
            act.finish()
        }
    }

    @Synchronized
    fun removeTopActivity(){
        val size = size()
        if(size > 0){
            activities.removeAt(size - 1)
        }
    }

}