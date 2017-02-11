package com.sakebook.android.sample.bottomnavigationviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.view.View


class MainActivity : AppCompatActivity() {

    private val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    private val bottomNavigation by lazy { findViewById(R.id.bottom_navigation) as BottomNavigationView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById(android.R.id.content).systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        setContentView(R.layout.activity_main)
        initToolbar()
        initBottomNavigation()
    }

    private fun initToolbar() {
        toolbar.title = resources.getString(R.string.app_name)
    }

    private fun initBottomNavigation() {
        val tabCount = 3
        val fragments = (0 until tabCount).mapIndexed { i, value ->
            MainFragment.newInstance(i)
        }
        updateBottomNavigation(fragments, 0, "0")
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
        }
    }

    private fun updateBottomNavigation(fragments: List<Fragment>, position: Int, tag: String): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        when(fragment) {
            null -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout_container, fragments[position], tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            else -> {
                if (fragment is BottomNavigationInterface) {
                    fragment.scrollToTop()
                }
            }
        }
        return true
    }
}
