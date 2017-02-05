package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * Created by sakemotoshinya on 2017/02/06.
 */
class BottomNavigationBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    private var defaultDependencyTop = -1

    override fun layoutDependsOn(parent: CoordinatorLayout, bottomBar: BottomNavigationView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, bottomBar: BottomNavigationView, dependency: View): Boolean {
        when(dependency) {
            is AppBarLayout -> {
                if (defaultDependencyTop == -1) {
                    defaultDependencyTop = dependency.top
                }
                ViewCompat.setTranslationY(bottomBar, (-dependency.top + defaultDependencyTop).toFloat())
            }
        }
        return true
    }
}