package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by sakemotoshinya on 2017/02/05.
 */
class MainFragment: Fragment() {

    companion object {
        val ARG_KEY_SEED = "arg_key_seed"
        @JvmStatic
        fun newInstance(seed: Int): Fragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_KEY_SEED, seed)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        val items = (0..120).map { "item: $it" }
        val args = arguments
        val seedId = args.getInt(ARG_KEY_SEED)
        recyclerView?.layoutManager = when(seedId) {
            1 -> GridLayoutManager(context, 3)
            2 -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            else -> LinearLayoutManager(context)
        }
        recyclerView?.adapter = MainAdapter(context, items)
    }

    fun smoothScrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }
}