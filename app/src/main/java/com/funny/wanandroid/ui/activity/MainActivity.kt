package com.funny.wanandroid.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.funny.appframework.base.BaseActivity
import com.funny.wanandroid.R
import com.funny.wanandroid.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private var homeFragment : Fragment? = null
    private var systemFragment : Fragment? = null
    private var publicFragment : Fragment? = null
    private var projectFragment : Fragment? = null
    private var mineFragment : Fragment? = null

    private var curIndex: Int = -1

    override fun getLayoutId(): Int {

        return R.layout.activity_main
    }

    override fun initViews(savedState: Bundle?) {
        setTabSelection(0)
        bottomNavView.setOnNavigationItemSelectedListener{
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

        when (index) {
            0 -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance()
                    transaction.add(R.id.flContent, homeFragment!!, HomeFragment::class.java.simpleName)
                } else {
                    transaction.show(homeFragment!!)
                }
            }
            1 -> {
                if (systemFragment == null) {
                    systemFragment = KnowledgeSystemFragment()
                    transaction.add(R.id.flContent, systemFragment!!, KnowledgeSystemFragment::class.java.simpleName)
                } else {
                    transaction.show(systemFragment!!)
                }
            }
            2 -> {
                if (publicFragment == null) {
                    publicFragment = PublicPlatformFragment()
                    transaction.add(R.id.flContent, publicFragment!!, PublicPlatformFragment::class.java.simpleName)
                } else {
                    transaction.show(publicFragment!!)
                }
            }
            3 -> {
                if (projectFragment == null) {
                    projectFragment = ProjectFragment()
                    transaction.add(R.id.flContent, projectFragment!!, ProjectFragment::class.java.simpleName)
                } else {
                    transaction.show(projectFragment!!)
                }
            }
            4 -> {
                if (mineFragment == null) {
                    mineFragment = MineFragment()
                    transaction.add(R.id.flContent, mineFragment!!, MineFragment::class.java.simpleName)
                } else {
                    transaction.show(mineFragment!!)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(homeFragment!!) }
        systemFragment?.let { transaction.hide(systemFragment!!) }
        publicFragment?.let { transaction.hide(publicFragment!!) }
        projectFragment?.let { transaction.hide(projectFragment!!) }
        mineFragment?.let { transaction.hide(mineFragment!!) }
    }

}

