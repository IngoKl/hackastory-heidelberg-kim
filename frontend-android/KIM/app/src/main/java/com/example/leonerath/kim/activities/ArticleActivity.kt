package com.example.leonerath.kim.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.leonerath.kim.R
import com.example.leonerath.kim.adapters.ViewPagerAdapter

import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)


        if (intent.getIntExtra("id",0) != 0){
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager, intent.getIntExtra("id",0))
        }else{
            finish()
        }





    }

}
