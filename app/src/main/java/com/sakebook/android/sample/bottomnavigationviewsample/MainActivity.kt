package com.sakebook.android.sample.bottomnavigationviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.os.Build
import android.view.View


class MainActivity : AppCompatActivity() {

    private val bottomNavigation by lazy { findViewById(R.id.bottom_navigation) as BottomNavigationView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        setContentView(R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val tabCount = 3
        val fragments = (0 until tabCount).mapIndexed { i, value ->
            MainFragment.newInstance(i)
        }
        supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragments[0], "0").commit()
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_recents -> {
                    updateBottomNavigation(fragments, 0, "0")
                }
                R.id.navigation_favorites -> {
                    updateBottomNavigation(fragments, 1, "1")
                }
                R.id.navigation_nearby -> {
                    updateBottomNavigation(fragments, 2, "2")
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
            true
        }
    }

    private fun updateBottomNavigation(fragments: List<Fragment>, potision: Int, tag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        when(fragment) {
            null -> supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragments[potision], tag).commit()
            else -> {
                (fragment as MainFragment).smoothScrollToTop()
            }
        }
    }
}
