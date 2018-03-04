package com.example.leonerath.kim.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.leonerath.kim.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.example.leonerath.kim.models.Article
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {


    override fun onMarkerClick(marker: Marker?): Boolean {
        Log.v("MainActivity","clicked")
        if (marker != null){
            var article = articleList.find { article -> article.id == marker.tag }
            if (article != null) {
                Log.v("MainActivity", "Clicked on" + article.headline)
                val id = article.id
                val intent = Intent(this, ArticleActivity::class.java).apply {
                    putExtra("id", id)
                }
                startActivity(intent)
                return true;
            }
        }

        return false
    }

    private var position = 1
    private var mMap: GoogleMap? = null

    // politik blau
    // sport rot
    // entertainment gelb
    // science grün
    override fun onMapReady(googleMap: GoogleMap?) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = googleMap
        loadData()
    }




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.politics -> {
                position =1

                resetMap()
                return@OnNavigationItemSelectedListener true
            }
            R.id.sports -> {
                position =2

                resetMap()
                return@OnNavigationItemSelectedListener true
            }
            R.id.entertainment -> {
                position =3

                resetMap()
                return@OnNavigationItemSelectedListener true
            }
            R.id.science -> {
                position =4
                resetMap()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun resetMap() {
        if (mMap != null){
            mMap!!.clear();
            addMarkersToMap();
        }
    }

    private fun clearMap() {
        if (mMap != null){
            mMap!!.clear()
        }
    }



    private fun addMarkersToMap() {
        if (mMap != null){
            if (articleList.size > 0){
                mMap!!.setOnMarkerClickListener(this)

                when(position){
                    1 -> {
                        var politicsList: List<Article> = articleList.filter{ article -> article.category == "Politics" }

                        for (article in politicsList){

                            val city = LatLng(article.lat,article.lng)
                            var marker = mMap!!.addMarker(MarkerOptions().position(city)
                                    .title(article.headline)
                            )
                            marker.tag = article.id
                            marker.setIcon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                        }
                    }
                    2 -> {
                        var sportsList: List<Article> = articleList.filter{ article -> article.category == "Sports" }

                        for (article in sportsList){

                            val city = LatLng(article.lat,article.lng)
                            var marker = mMap!!.addMarker(MarkerOptions().position(city)
                                    .title(article.headline)
                            )
                            marker.tag = article.id
                            marker.setIcon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                        }
                    }
                    3 -> {
                        var entertainmentList: List<Article> = articleList.filter{ article -> article.category == "Entertainment" }

                        for (article in entertainmentList){

                            val city = LatLng(article.lat,article.lng)
                            var marker = mMap!!.addMarker(MarkerOptions().position(city)
                                    .title(article.headline)
                            )
                            marker.tag = article.id
                            marker.setIcon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

                        }
                    }
                    4 -> {
                        var scienceList: List<Article> = articleList.filter{ article -> article.category == "Science" }

                        Log.v("MainActivity","ScienceList"+scienceList.size)
                        for (article in scienceList){

                            val city = LatLng(article.lat,article.lng)
                            var marker = mMap!!.addMarker(MarkerOptions().position(city)
                                    .title(article.headline)
                            )
                            marker.tag = article.id
                            marker.setIcon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

                        }
                    }
                }
            }
        }
    }







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

       // navigation.itemTextColor = ColorStateList(states,colors1 )
       // navigation.itemIconTintList = ColorStateList(states,colors1 )


    }

    var articleList : MutableList<Article> = mutableListOf<Article>()

    private fun loadData() {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.52.1.114:8080/api/article/full_list"


        val stringRequest = StringRequest(Request.Method.GET, url,
                object : Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.v("MainAcitivity",response)

                        var jsonArray = JSONArray(response)
                        for (i in 0..(jsonArray.length() - 1)) {
                            val jsonObject = jsonArray.getJSONObject(i)

                            val article: Article = gson.fromJson(jsonObject.toString(), Article::class.java)
                            Log.v("MainActivity","Article: "+article)
                            articleList.add(article)
                        }
                        addMarkersToMap()

                    }
                }, object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                    Log.v("MainActivity","Could not load Data")
            }
        })
        queue.add(stringRequest)
    }


}

