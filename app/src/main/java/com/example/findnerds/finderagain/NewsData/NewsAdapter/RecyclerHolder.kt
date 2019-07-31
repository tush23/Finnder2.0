package com.example.findnerds.finderagain.NewsData.NewsAdapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findnerds.finderagain.R

class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val newsHeadingText  = itemView.findViewById<TextView>(R.id.news_heading)!!
    val newsImage = itemView.findViewById<ImageView>(R.id.news_header_img)!!
    val newsSource = itemView.findViewById<Button>(R.id.tvsource)!!


}