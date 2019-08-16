package com.funny.wanandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author pengl
 */

class CommonViewPagerAdapter(fm : FragmentManager?, val paramList : List<String>, val fragments : List<Fragment>) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position in 0..paramList.size){
            return paramList[position]
        }
        return ""
    }

}