package com.example.leonerath.kim.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.leonerath.kim.fragments.*


/**
 * Created by leonerath on 04.03.18.
 */


class ViewPagerAdapter2(fm: FragmentManager?, articleId : Int, score2:Int) : FragmentPagerAdapter(fm) {

    var id = articleId
    var score = score2

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("id",id )
        bundle.putInt("score",score )
        when(position){
            0 -> {
                val fragment = SolutionFragment()
                fragment.arguments = bundle
                return fragment}
            1 -> {
                val fragment = ScoreFragment()
                fragment.arguments = bundle
                return fragment}
            else -> {
                Log.e("ViewPagerAdapter", "Error getItem()")
                return SolutionFragment()
            }
        }
    }


    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return super.getPageTitle(position)
    }

}