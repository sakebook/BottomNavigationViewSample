package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Created by sakemotoshinya on 2017/02/06.
 */
class BottomNavigationBehavior(val context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    private var isAnimate = false
    private var lastPosition = 0
    private var defaultTop = -1
    private var defaultBottom = -1
    private val THRESHOLD = 20
    private var snackBarDefaultPaddingBottom = -1
    private var snacking = false

    override fun layoutDependsOn(parent: CoordinatorLayout, bottomBar: BottomNavigationView, dependency: View): Boolean {
        return dependency is AppBarLayout || dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, bottomBar: BottomNavigationView, dependency: View): Boolean {
        when(dependency) {
            is AppBarLayout -> {
                if (snacking) {
                    // Do not animation while the Snackbar is fixed.
                    return true
                }
                bottomBehavior(bottomBar, dependency)
            }
            is Snackbar.SnackbarLayout -> {
                snacking = dependency.top == dependency.y.toInt() // If true, Snackbar is completely displayed.
                snackbarBehavior(bottomBar, dependency)
            }
        }
        return true
    }

    private fun bottomBehavior(bottomBar: BottomNavigationView, dependency: View) {
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

    private fun snackbarBehavior(bottomBar: BottomNavigationView, dependency: View) {
        if (snackBarDefaultPaddingBottom == -1) {
            snackBarDefaultPaddingBottom = dependency.paddingBottom
        }
        val diff = bottomBar.bottom - bottomBar.y
        val padding = when(bottomBar.bottom == bottomBar.y.toInt()) {
            true -> context.resources.getDimension(R.dimen.navigation_bar_padding)
            false -> 0f
        }
        ViewCompat.setPaddingRelative(dependency, dependency.paddingStart, dependency.paddingTop, dependency.paddingEnd, (diff + padding).toInt())
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