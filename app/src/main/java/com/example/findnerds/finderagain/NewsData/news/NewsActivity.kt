package com.example.findnerds.finderagain.NewsData.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.findnerds.finderagain.Intro.Intro_Activity
import com.example.findnerds.finderagain.R
import com.example.findnerds.finderagain.SignIn.BottomDrawerFrag
import kotlinx.android.synthetic.main.bottomappbar.*

class NewsActivity : AppCompatActivity() {
    private val bottomNavDrawerFragment = BottomDrawerFrag()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity)
        setSupportActionBar(bottomAppBar)
        bottomAppBar.setNavigationOnClickListener {
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }

         val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        // Check if we need to display our OnboardingFragment
        if (!sharedPreferences.getBoolean("AppIntroFragment",false)) {
            // The user hasn't seen the OnboardingFragment yet, so show it
            startActivity(Intent(this, Intro_Activity::class.java))
            sharedPreferences.edit().putBoolean("AppIntroFragment",true).apply()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NewsFragment.newInstance())
                    .commitNow()
        }

    }

}
