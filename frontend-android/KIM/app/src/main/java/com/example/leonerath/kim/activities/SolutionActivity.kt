package com.example.leonerath.kim.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.leonerath.kim.R
import com.example.leonerath.kim.adapters.ViewPagerAdapter
import com.example.leonerath.kim.adapters.ViewPagerAdapter2
import kotlinx.android.synthetic.main.activity_solution.*

class SolutionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)

        val id = intent.getIntExtra("id",0)
        val score = intent.getIntExtra("score",10)

        if (id != 0 && score != 10){
            Log.v("SolutionActivity", " "+score)
            viewPager.adapter = ViewPagerAdapter2(supportFragmentManager, id,score)
        }else{
            finish()
        }
    }
}
