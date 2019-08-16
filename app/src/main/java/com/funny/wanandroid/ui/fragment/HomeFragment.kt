package com.funny.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.funny.appframework.base.BaseFragment
import com.funny.wanandroid.R
import com.funny.wanandroid.ui.adapter.CommonViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author pengl
 */
class HomeFragment : BaseFragment(){
    private var pagerAdapter : CommonViewPagerAdapter? = null

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(rootView: View, savedState: Bundle?) {
        tabs.tabMode = TabLayout.MODE_SCROLLABLE
        val titles = arrayListOf("最新博文","最新项目")

        tabs.addTab(tabs.newTab().setText(titles[0]))
        tabs.addTab(tabs.newTab().setText(titles[1]))

        val titleList = arrayListOf<String>()
        val fragments = arrayListOf<Fragment>()

        for ((index,title) in titles.withIndex()){
            titleList.add(title)
            tabs.addTab(tabs.newTab().setText(title))
            if(index == 0)
                fragments.add(HomeArticleListFragment())
            if(index == 1)
                fragments.add(HomeProjectListFragment())
        }

        pagerAdapter = CommonViewPagerAdapter(fragmentManager, titleList, fragments)

        pager.adapter = pagerAdapter
        pager.currentItem = 0
    }

    override fun initData() {

    }

}