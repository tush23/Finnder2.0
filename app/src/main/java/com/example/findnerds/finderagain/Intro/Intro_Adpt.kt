package com.example.findnerds.finderagain.Intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.findnerds.finderagain.R

import java.util.ArrayList


/**
 * Created by Jaison
 */


internal class Intro_Adpt(private val mContext: Context, items: ArrayList<Intro_Items>) : PagerAdapter() {
    var intro_Item = ArrayList<Intro_Items>()


    init {
        this.intro_Item = items
    }

    override fun getCount(): Int {
        return intro_Item.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.activity_introitem, container, false)

        val item = intro_Item[position]

        val imageView = itemView.findViewById<View>(R.id.intro_img) as ImageView
        imageView.setImageResource(item.imageID)

        val tv_title = itemView.findViewById<View>(R.id.intro_header) as TextView
        tv_title.text = item.title

        val tv_content = itemView.findViewById<View>(R.id.intro_desc) as TextView
        tv_content.text = item.description

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}
