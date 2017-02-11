package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout

/**
 * Created by sakemotoshinya on 2017/02/06.
 */
class BottomLayoutBehavior(val context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<LinearLayout>(context, attrs) {

    private val THRESHOLD = 20
    private var isAnimate = false
    private var lastPosition = 0
    private var snacking = false

    override fun layoutDependsOn(parent: CoordinatorLayout, bottomLayout: LinearLayout, dependency: View): Boolean {
        return dependency is AppBarLayout || dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, bottomLayout: LinearLayout, dependency: View): Boolean {
        when(dependency) {
            is AppBarLayout -> {
                if (snacking) {
                    // Do not animation while the Snackbar is fixed.
                    return true
                }
                bottomLayoutChange(bottomLayout, dependency)
            }
            is Snackbar.SnackbarLayout -> {
                snacking = dependency.top == dependency.y.toInt() // If true, Snackbar is completely displayed.
                snackbarChange(bottomLayout, dependency)
            }
        }
        return true
    }

    private fun bottomLayoutChange(bottomLayout: LinearLayout, dependency: View) {
        val diff = Math.abs(lastPosition - dependency.top)
        val scrollLimit = dependency.top - dependency.bottom

        if (lastPosition == dependency.top && lastPosition == 0) { // on top limit
            animation(bottomLayout, true)
        } else if (lastPosition == dependency.top && lastPosition == scrollLimit) { // on bottom limit
            animation(bottomLayout, false)
        } else if (diff > THRESHOLD && lastPosition < dependency.y) { // go to top
            animation(bottomLayout, true)
        } else if (diff > THRESHOLD && lastPosition > dependency.y) { // go to bottom
            animation(bottomLayout, false)
        } else {
            // nothing
        }
        lastPosition = dependency.y.toInt()
    }

    private fun snackbarChange(bottomLayout: LinearLayout, dependency: View) {
        val diff = bottomLayout.bottom - bottomLayout.y
        // If bottom layout is collapsing, need navigation bar padding.
        val padding = when(bottomLayout.bottom == bottomLayout.y.toInt()) {
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