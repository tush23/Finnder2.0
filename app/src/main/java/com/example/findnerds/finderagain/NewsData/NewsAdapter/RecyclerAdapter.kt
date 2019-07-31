package com.example.findnerds.finderagain.NewsData.NewsAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.findnerds.finderagain.R
import com.example.findnerds.finderagain.NewsData.NewsResponse.NewsResponse
import com.squareup.picasso.Picasso

class RecyclerAdapter(var context:Context, var data: NewsResponse) : RecyclerView.Adapter<RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_items,parent,false)

        return RecyclerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.articles.size
    }

    override fun onBindViewHolder(recyclerHolder: RecyclerHolder, position: Int) {
        recyclerHolder.newsHeadingText.text = data.articles[position].title
        recyclerHolder.newsSource.text = data.articles[position].source.name
        if (data.articles[position].urlToImage!!.isNotEmpty()) {
            Picasso.get()
                   .load(data.articles[position]
                   .urlToImage)
                   .into(recyclerHolder.newsImage)
        }
        recyclerHolder.itemView.setOnClickListener {
            Toast.makeText(context,data.articles[position].title,Toast.LENGTH_SHORT).show()
        }
    }

}