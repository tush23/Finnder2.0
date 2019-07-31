package com.example.findnerds.finderagain.Intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.findnerds.finderagain.NewsData.news.NewsActivity
import com.example.findnerds.finderagain.R
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.*


class Intro_Activity : AppCompatActivity() {


    private var pager_indicator: LinearLayout? = null
    private var dotsCount: Int = 0
    private var dots: Array<ImageView?>? = null
    private var intro_pager: ViewPager? = null
    private var mAdapter: Intro_Adpt? = null
    private var btn_get_started: Button? = null
    internal var previous_pos = 0
    internal var introItems = ArrayList<Intro_Items>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btn_get_started = findViewById<View>(R.id.btn_get_started) as Button
        intro_pager = findViewById<View>(R.id.pager_introduction) as ViewPager
        pager_indicator = findViewById<View>(R.id.viewPagerCountDots) as LinearLayout

        loadData()

        mAdapter = Intro_Adpt(this, introItems)
        intro_pager!!.adapter = mAdapter

        view_Pager_Anim(intro_pager!!)

        intro_pager!!.currentItem = 0
        intro_pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                // Change the current position intimation

                for (i in 0 until dotsCount) {
                    dots!![i]!!.setImageDrawable(ContextCompat.getDrawable(this@Intro_Activity,
                        R.drawable.non_selected_item_dot
                    ))
                }

                dots!![position]!!.setImageDrawable(ContextCompat.getDrawable(this@Intro_Activity,
                    R.drawable.selected_item_dot
                ))


                val pos = position + 1

                if (pos == dotsCount && previous_pos == dotsCount - 1)
                { show_btn_anim()
                    hide_dots_anim()}
                else if (pos == dotsCount - 1 && previous_pos == dotsCount)
                {  hide_btn_anim()
                    show_dots_anim()}

                previous_pos = pos
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        btn_get_started!!.setOnClickListener {
            Toast.makeText(this@Intro_Activity, "Redirect to wherever you want", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, NewsActivity::class.java))
        }

        setUiPageViewController()

    }

    // Load data into the viewpager
    fun loadData() {

        val header = intArrayOf(R.string.intro_title_1, R.string.intro_title_2, R.string.intro_title_3)
        val desc = intArrayOf(R.string.intro_desc_1, R.string.intro_desc_2, R.string.intro_desc_3)
        val imageId = intArrayOf(R.drawable.girlintro1, R.drawable.boyintro2, R.drawable.boyintro3)

        for (i in imageId.indices) {
            val item = Intro_Items()
            item.imageID = imageId[i]
            item.title = resources.getString(header[i])
            item.description = resources.getString(desc[i])

            introItems.add(item)
        }
    }

    //Animate viewPager Items
    fun view_Pager_Anim(intro_pager: ViewPager){
        intro_pager.setPageTransformer(true) { view, position ->
            val pageWidth = view.width

            when {
                position == 0.0f -> // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.alpha = 1f
                position <= 1 -> { // [-1,1]
                    val dummyImageView = view.findViewById<ImageView>(R.id.intro_img)
                    val myAnim = AnimationUtils.loadAnimation(this@Intro_Activity, R.anim.bounce)
                    val mybounceinter = Bounce_InterPolator(0.01, 5.0)
                    myAnim.interpolator = mybounceinter
                    // dummyImageView.startAnimation(myAnim)
                    dummyImageView.translationX = position * (pageWidth)  // Half
                    val imageBottom = view
                            .findViewById<TextView>(R.id.intro_header)
                    imageBottom.translationX = position * (pageWidth / 2) // Half speed
                }
                else -> // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.alpha = 1f
            }
        }
    }

    // Button bottomUp animation
    fun show_btn_anim() {
        val show = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim)

        btn_get_started!!.startAnimation(show)

        show.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {
                btn_get_started!!.visibility = View.VISIBLE
            }
            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                btn_get_started!!.clearAnimation()

            }
        })

    }

    // Button Top to down animation
    fun hide_btn_anim() {
        val hide = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim)

        btn_get_started!!.startAnimation(hide)

        hide.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                btn_get_started!!.clearAnimation()
                btn_get_started!!.visibility = View.GONE

            }

        })


    }
    fun show_dots_anim(){
        viewPagerCountDots.visibility =View.VISIBLE
    }
    fun hide_dots_anim(){
        val hide = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim)
        viewPagerCountDots.startAnimation(hide)
        hide.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {}

            override fun onAnimationStart(p0: Animation?) {
                viewPagerCountDots.visibility = View.GONE
            }

        })
    }

    // setup the UI for dots or indicator
    private fun setUiPageViewController() {

        dotsCount = mAdapter!!.getCount()
        dots = arrayOfNulls(dotsCount)

        for (i in 0 until dotsCount) {
            dots!![i] = ImageView(this)
            dots!![i]!!.setImageDrawable(ContextCompat.getDrawable(this@Intro_Activity,
                R.drawable.non_selected_item_dot
            ))

            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(6, 0, 6, 0)

            pager_indicator!!.addView(dots!![i], params)
        }

        dots!![0]!!.setImageDrawable(ContextCompat.getDrawable(this@Intro_Activity, R.drawable.selected_item_dot))
    }
    /*fun ImageAnimation(){
        //val it: Intro_Items? = null
        //val img = it!!.animdata
        val myAnim = AnimationUtils.loadAnimation(this@Intro_Activity, R.anim.bounce)
        val mybounceinter =Bounce_InterPolator(0.1,20.0)
        myAnim.interpolator = mybounceinter
        intro_pager!!.startAnimation(myAnim)

    }*/
}
