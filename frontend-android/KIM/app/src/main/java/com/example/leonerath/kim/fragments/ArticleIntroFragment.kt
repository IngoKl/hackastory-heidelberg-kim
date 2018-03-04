package com.example.leonerath.kim.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_article_intro.*
import org.json.JSONArray

/**
 * A placeholder fragment containing a simple view.
 */
class ArticleIntroFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val id = arguments.getInt("id",0)

        if (id != 0){
            loadData(id)
        }

        return inflater.inflate(R.layout.fragment_article_intro, container, false)
    }


    private fun loadData(id: Int) {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url = "http://10.52.1.114:8080/api/article/"+id


        val stringRequest = StringRequest(Request.Method.GET, url,
                object : Response.Listener<String> {
                    override fun onResponse(response: String) {
                        Log.v("MainAcitivity",response)

                        val article: Article = gson.fromJson(response, Article::class.java)
                        showArticle(article)
                    }
                }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.v("MainActivity","Could not load Data")
            }
        })
        queue.add(stringRequest)
    }

    private fun showArticle(article: Article) {
        textViewHeadline.text = article.headline
        textViewAbstract.text = article.abstract

    }

}
