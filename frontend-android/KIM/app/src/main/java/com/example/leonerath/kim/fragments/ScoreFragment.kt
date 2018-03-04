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
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.leonerath.kim.R
import com.example.leonerath.kim.models.Article
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_score.*

import kotlinx.android.synthetic.main.fragment_score.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ScoreFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_score, container, false)
        val score = arguments.getInt("score",0)

        val result = 100 - (score*25)



        rootView.button.setOnClickListener {
            activity.finish()
        }

        rootView.textViewScore.text = "+"+result+"!"

        // Inflate the layout for this fragment
        return rootView
    }




}// Required empty public constructor
