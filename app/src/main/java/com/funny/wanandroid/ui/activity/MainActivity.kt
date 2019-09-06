package com.funny.wanandroid.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.funny.appframework.base.showToast
import com.funny.appframework.utils.LogUtil
import com.funny.wanandroid.R
import com.funny.wanandroid.common.BaseSwipeBackActivity
import com.funny.wanandroid.ui.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseSwipeBackActivity() {

    private var homeFragment : Fragment?= null
    private var systemFragment : Fragment?= null
    private var publicFragment : Fragment?= null
    private var projectFragment : Fragment?= null
    private var mineFragment : Fragment?= null

    private var curIndex: Int = -1

    private var bottomNavView : BottomNavigationView? = null

    override fun setupCustomToolbar(): Boolean {
        LogUtil.i("MainActivity shouldSetupCustomToolbar")
        setContentView(getLayoutId(),true,0)
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(savedState: Bundle?) {
        setTabSelection(0)
        bottomNavView = bottom_nav_view
        bottomNavView?.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.navigation_home -> setTabSelection(0)
                R.id.navigation_type -> setTabSelection(1)
                R.id.navigation_wx -> setTabSelection(2)
                R.id.navigation_project -> setTabSelection(3)
                R.id.navigation_my -> setTabSelection(4)
            }

            return@setOnNavigationItemSelectedListener true
        }
    }


    override fun initData() {
    }

    private fun setTabSelection(index: Int) {
        if (index == curIndex) return

        // 开启一个Fragment事务
        val transaction = supportFragmentManager.beginTransaction()

        curIndex = index

        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction)

        var fragment : Fragment? = null
        var isShow = false

        when (index) {
            0 -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance()
                    isShow = false
                } else {
                    isShow = true
                }

                fragment = homeFragment
            }
            1 -> {
                if (systemFragment == null) {
                    systemFragment = KnowledgeSystemFragment()
                    isShow = false
                } else {
                    isShow = true
                }

                fragment = systemFragment
            }
            2 -> {
                if (publicFragment == null) {
                    publicFragment = PublicPlatformFragment()
                    isShow = false
                } else {
                    isShow = true
                }

                fragment = publicFragment
            }
            3 -> {
                if (projectFragment == null) {
                    projectFragment = ProjectFragment()
                    isShow = false
                } else {
                    isShow = true
                }

                fragment = projectFragment
            }
            4 -> {
                if (mineFragment == null) {
                    mineFragment = MineFragment()
                    isShow = false
                } else {
                    isShow = true
                }

                fragment = mineFragment
            }
        }

        fragment?.let {
            if(isShow) showFragment(transaction,it)
            else addFragment(transaction,it)
        }

        transaction.commitAllowingStateLoss()
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        systemFragment?.let { transaction.hide(it) }
        publicFragment?.let { transaction.hide(it) }
        projectFragment?.let { transaction.hide(it) }
        mineFragment?.let { transaction.hide(it) }
    }

    private fun addFragment(transaction: FragmentTransaction,fragment: Fragment){
        transaction.add(R.id.fl_content,fragment,fragment.javaClass.simpleName)
    }

    private fun showFragment(transaction: FragmentTransaction,fragment: Fragment){
        transaction.show(fragment)
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    var lastClickTime = 0L

    override fun onBackPressed() {
        val curClickTime = System.currentTimeMillis()
        if(curClickTime - lastClickTime >= 2000){
            lastClickTime = curClickTime
            showToast(R.string.msg_back_app)
            return
        }

        finish()
    }

}

