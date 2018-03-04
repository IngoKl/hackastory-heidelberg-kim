package com.example.leonerath.kim.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.leonerath.kim.fragments.*
import android.os.Bundle



/**
 * Created by leonerath on 04.03.18.
 */


class ViewPagerAdapter(fm: FragmentManager?, articleId : Int) : FragmentPagerAdapter(fm) {

    var id = articleId

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("id",id )
        when(position){
            0 -> {
                val fragment = ArticleIntroFragment()
                fragment.arguments = bundle
                return fragment}
            1 -> {
                val fragment = BackgroundInformationFragment()
                fragment.arguments = bundle
                return fragment}
            2 -> {
                val fragment = ArticleMainFragment()
                fragment.arguments = bundle
                return fragment}
            3 -> {
                val fragment = QuizFragment()
                fragment.arguments = bundle
                return fragment}
            else -> {
                Log.e("ViewPagerAdapter", "Error getItem()")
                return ArticleIntroFragment()
            }
        }
    }


    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return super.getPageTitle(position)
    }

}