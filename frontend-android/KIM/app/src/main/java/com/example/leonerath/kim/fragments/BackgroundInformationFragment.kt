package com.example.leonerath.kim.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.leonerath.kim.R
import com.example.leonerath.kim.models.Article
import com.example.leonerath.kim.models.BgInformation
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_background_information.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BackgroundInformationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BackgroundInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BackgroundInformationFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val id = arguments.getInt("id",0)

        if (id != 0){
            loadData(id)
        }

        return inflater!!.inflate(R.layout.fragment_background_information, container, false)
    }

    lateinit var queue:RequestQueue

    private fun loadData(id: Int) {


        val gson = Gson()
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(activity)

        val url = "http://10.52.1.114:8080/api/article/"+id


        val stringRequest = StringRequest(Request.Method.GET, url,
                object : Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.v("QuizFragment",response)

                        var article = gson.fromJson(response, Article::class.java)

                        loadInfo("http://10.52.1.114:8080/api/information/"+article.background_information_id)



                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.v("MainActivity","Could not load Data")
            }
        })







        queue.add(stringRequest)
    }

    private fun loadInfo(url: String) {
        val gson = Gson()
        // Instantiate the RequestQueue.




        val stringRequest = StringRequest(Request.Method.GET, url,
                object : Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.v("MainAcitivity",response)

                        val info: BgInformation = gson.fromJson(response, BgInformation::class.java)
                        showArticle(info)
                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.v("MainActivity","Could not load Data")
            }
        })
        queue.add(stringRequest)
    }

    private fun showArticle(info: BgInformation) {
        textViewBackground.text = info.content.get(0).get(1)
    }

}
