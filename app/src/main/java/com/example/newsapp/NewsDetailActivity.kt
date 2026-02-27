package com.example.newsapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "News Detail"

        val imageView = findViewById<ImageView>(R.id.imageViewDetail)
        val title = findViewById<TextView>(R.id.textViewDetailTitle)
        val date = findViewById<TextView>(R.id.textViewDetailDate)
        val content = findViewById<TextView>(R.id.textViewDetailContent)

        val imageUrl = intent.getStringExtra("image")
        val newsTitle = intent.getStringExtra("title")
        val newsDate = intent.getStringExtra("date")
        val newsContent = intent.getStringExtra("content")

        title.text = newsTitle
        date.text = newsDate
        content.text = newsContent

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}