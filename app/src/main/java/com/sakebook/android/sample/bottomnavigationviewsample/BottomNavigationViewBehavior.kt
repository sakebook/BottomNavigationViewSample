package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Created by sakemotoshinya on 2017/02/06.
 */
class BottomNavigationBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    private var isAnimate = false
    private var lastPosition = 0
    private var defaultTop = -1
    private var defaultBottom = -1
    private val THRESHOLD = 20

    override fun layoutDependsOn(parent: CoordinatorLayout, bottomBar: BottomNavigationView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, bottomBar: BottomNavigationView, dependency: View): Boolean {
        when(dependency) {
            is AppBarLayout -> {
                if (defaultTop == -1 && defaultBottom == -1) {
                    defaultTop = bottomBar.top
                    defaultBottom = bottomBar.bottom
                }
                val position = dependency.top
                val diff = Math.abs(lastPosition - position)
                if (lastPosition == position && lastPosition == 0) { // on top
                    if (defaultBottom == bottomBar.y.toInt()) { // scroll limit on top
                        animation(bottomBar, true)
                    }
                } else if (lastPosition == position && lastPosition == (dependency.top - dependency.bottom)) { // on bottom
                    if (defaultTop == bottomBar.y.toInt()) { // scroll limit on bottom
                        animation(bottomBar, false)
                    }
                } else if (diff > THRESHOLD && lastPosition < position) { // to top
                    animation(bottomBar, true)
                } else if (diff > THRESHOLD && lastPosition > position) { // to bottom
                    animation(bottomBar, false)
                } else {
                    // nothing
                }
                lastPosition = position
            }
        }
        return true
    }

    private fun animation(view: View, reverse: Boolean) {
        if (isAnimate) {
            return
        }
        isAnimate = true
        val animateValue = (view.bottom - view.top).toFloat()
        ViewCompat.animate(view)
                .setDuration(250)
                .translationY(if (reverse) 0f else animateValue)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationEnd(view: View?) {
                        isAnimate = false
                    }

                    override fun onAnimationCancel(view: View?) {
                        isAnimate = false
                    }

                    override fun onAnimationStart(view: View?) {
                        isAnimate = true
                    }
                })
                .start()
    }
}