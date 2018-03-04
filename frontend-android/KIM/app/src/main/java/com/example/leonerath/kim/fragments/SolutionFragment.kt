package com.example.leonerath.kim.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.leonerath.kim.R
import com.example.leonerath.kim.models.Quiz
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.fragment_solution.*


/**
 * A simple [Fragment] subclass.
 */
class SolutionFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val id = arguments.getInt("id",0)

        if (id != 0){
            loadData(id)
        }

        return inflater!!.inflate(R.layout.fragment_solution, container, false)
    }


    private fun loadData(id: Int) {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url = "http://10.52.1.114:8080/api/quiz/"+id


        val stringRequest = StringRequest(Request.Method.GET, url,
                object : Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.v("SolutionFragment",response)

                        var quiz = gson.fromJson(response, Quiz::class.java)
                        showArticle(quiz!!)
                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.v("SolutionFragment","Could not load Data")
            }
        })
        queue.add(stringRequest)
    }

    private fun showArticle(quiz: Quiz) {
        textViewSolution.text = quiz.solution

    }

}
