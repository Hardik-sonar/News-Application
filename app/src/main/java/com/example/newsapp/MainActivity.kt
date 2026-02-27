package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.model.Article
import com.example.newsapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import android.widget.ProgressBar
import android.view.View
import android.widget.Toast
import android.widget.ImageView
class MainActivity : AppCompatActivity() {


    private val API_KEY = ""

    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private lateinit var profileIcon: ImageView
    private var fullArticleList = mutableListOf<Article>()

    private lateinit var progressBar: ProgressBar


    private lateinit var recyclerView: RecyclerView

    private lateinit var textViewError: TextView
    private lateinit var buttonRetry: Button
    private lateinit var newsAdapter: NewsAdapter
    private var articleList = mutableListOf<Article>()

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        profileIcon = findViewById(R.id.imageViewProfileIcon)

        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        searchView = findViewById(R.id.searchView)
        setupSearch()

        textViewError = findViewById(R.id.textViewError)
        buttonRetry = findViewById(R.id.buttonRetry)

        buttonRetry.setOnClickListener {
            fetchNews()
        }

        progressBar = findViewById(R.id.progressBar)

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

        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {

                val response = RetrofitInstance.api.getTopHeadlines(
                    country = "in",
                    apiKey = API_KEY
                )
                textViewError.visibility = View.GONE
                buttonRetry.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {

                    val newsResponse = response.body()
                    fullArticleList = newsResponse?.articles?.toMutableList() ?: mutableListOf()

                    if (fullArticleList.isEmpty()) {
                        Toast.makeText(this@MainActivity,
                            "No news available",
                            Toast.LENGTH_SHORT).show()
                        return@launch
                    }
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    textViewError.visibility = View.GONE
                    buttonRetry.visibility = View.GONE

                    articleList = fullArticleList.toMutableList()

                    newsAdapter = NewsAdapter(articleList) { article ->

                        val intent = Intent(this@MainActivity, NewsDetailActivity::class.java)
                        intent.putExtra("title", article.title)
                        intent.putExtra("date", article.publishedAt)
                        intent.putExtra("content", article.content)
                        intent.putExtra("image", article.urlToImage)

                        startActivity(intent)
                    }

                    recyclerView.adapter = newsAdapter

                } else {

                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE

                    textViewError.text = "Failed to load news (Error ${response.code()})"
                    textViewError.visibility = View.VISIBLE
                    buttonRetry.visibility = View.VISIBLE
                }

            } catch (e: Exception) {

                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE

                textViewError.text = "Network error. Please check your internet connection."
                textViewError.visibility = View.VISIBLE
                buttonRetry.visibility = View.VISIBLE
            }
        }
    }
    private fun setupSearch() {

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val filteredList = fullArticleList.filter { article ->
                    article.title?.contains(newText ?: "", ignoreCase = true) == true ||
                            article.description?.contains(newText ?: "", ignoreCase = true) == true
                }

                newsAdapter = NewsAdapter(filteredList) { article ->

                    val intent = Intent(this@MainActivity, NewsDetailActivity::class.java)
                    intent.putExtra("title", article.title)
                    intent.putExtra("date", article.publishedAt)
                    intent.putExtra("content", article.content)
                    intent.putExtra("image", article.urlToImage)

                    startActivity(intent)
                }

                recyclerView.adapter = newsAdapter

                return true
            }
        })
    }
}