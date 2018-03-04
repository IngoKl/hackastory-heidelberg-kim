package com.example.leonerath.kim.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.example.leonerath.kim.activities.ArticleActivity
import com.example.leonerath.kim.activities.SolutionActivity
import com.example.leonerath.kim.models.Article
import com.example.leonerath.kim.models.Quiz
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.fragment_quiz.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuizFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_quiz, container, false)

        val id = arguments.getInt("id",0)

        if (id != 0){
            loadData(id)

        var wrong = 0

        rootView.button1.setOnClickListener {
            if (quiz!= null){
                if (quiz!!.answers.get(0).get(1) == "true"){
                    button1.setBackgroundColor(Color.GREEN)

                    val intent = Intent(context, SolutionActivity::class.java).apply {
                        putExtra("id", quiz!!.id)
                        putExtra("score", wrong)
                    }
                    startActivity(intent)
                    activity.finish()
                }else{
                    button1.setBackgroundColor(Color.RED)
                    wrong++
                }
            }



        }
        rootView.button2.setOnClickListener {
            if (quiz!= null) {
                if (quiz!!.answers.get(1).get(1) == "true") {
                    button2.setBackgroundColor(Color.GREEN)

                    val intent = Intent(context, SolutionActivity::class.java).apply {
                        putExtra("id", quiz!!.id)
                        putExtra("score", wrong)
                    }
                    startActivity(intent)
                    activity.finish()
                } else {
                    button2.setBackgroundColor(Color.RED)
                    wrong++
                }
            }

        }
        rootView.button3.setOnClickListener {
            if (quiz!= null) {
                if (quiz!!.answers.get(2).get(1) == "true") {
                    button3.setBackgroundColor(Color.GREEN)

                    val intent = Intent(context, SolutionActivity::class.java).apply {
                        putExtra("id", quiz!!.id)
                        putExtra("score", wrong)
                    }
                    startActivity(intent)
                    activity.finish()
                } else {
                    button3.setBackgroundColor(Color.RED)
                    wrong++
                }
            }

        }
        rootView.button4.setOnClickListener {
            if (quiz!= null) {
                if (quiz!!.answers.get(3).get(1) == "true") {
                    button4.setBackgroundColor(Color.GREEN)

                    val intent = Intent(context, SolutionActivity::class.java).apply {
                        putExtra("id", quiz!!.id)
                        putExtra("score", wrong)
                    }
                    startActivity(intent)
                    activity.finish()
                } else {
                    button4.setBackgroundColor(Color.RED)
                    wrong++
                }
            }

        }

        }


        // Inflate the layout for this fragment
        return rootView
    }

    var quiz: Quiz ?= null

    lateinit var queue :RequestQueue



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

                        loadQuitData("http://10.52.1.114:8080/api/quiz/"+article.quiz_id)



                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.v("MainActivity","Could not load Data")
            }
        })







        queue.add(stringRequest)
    }


    fun loadQuitData(url: String){
        val gson = Gson()

        val stringRequest = StringRequest(Request.Method.GET, url,
                object : Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.v("QuizFragment",response)

                        quiz = gson.fromJson(response, Quiz::class.java)
                        showArticle(quiz!!)
                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.v("MainActivity","Could not load Data")
            }
        })



        queue.add(stringRequest)

    }

    private fun showArticle(quiz: Quiz) {
        textViewQuestion.text = quiz.question

        button1.text = quiz.answers.get(0).get(0)
        button2.text = quiz.answers.get(1).get(0)
        button3.text = quiz.answers.get(2).get(0)
        button4.text = quiz.answers.get(3).get(0)

    }



}// Required empty public constructor
