package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.model.Article
import com.example.newsapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val API_KEY = ""

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private var articleList = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "News"

        recyclerView = findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchNews()
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }

    private fun fetchNews() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getTopHeadlines(
                    country = "in",
                    apiKey = API_KEY
                )

                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    articleList = newsResponse?.articles?.toMutableList() ?: mutableListOf()

                    newsAdapter = NewsAdapter(articleList)
                    recyclerView.adapter = newsAdapter

                } else {
                    Log.e("NEWS_ERROR", "Code: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("NETWORK_EXCEPTION", e.message ?: "Exception occurred")
            }
        }
    }
}