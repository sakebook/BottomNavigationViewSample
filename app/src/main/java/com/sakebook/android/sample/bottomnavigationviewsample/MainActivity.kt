package com.sakebook.android.sample.bottomnavigationviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottomNavigation by lazy { findViewById(R.id.bottom_navigation) as BottomNavigationView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val tabCount = 3
        val fragments = (0 until tabCount).mapIndexed { i, value ->
            MainFragment.newInstance(i)
        }

        supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragments[0]).commit()
        bottomNavigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when(it.itemId) {
                R.id.navigation_recents -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragments[0]).commit()
                    true
                }
                R.id.navigation_favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragments[1]).commit()
                    true
                }
                R.id.navigation_nearby -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_container, fragments[2]).commit()
                    true
                }
                else -> false
            }
        }
    }
}
