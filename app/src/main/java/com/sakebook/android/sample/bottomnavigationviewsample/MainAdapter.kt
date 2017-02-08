package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by sakemotoshinya on 2017/02/06.
 */
class MainAdapter(val context: Context, val items: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val inflater = LayoutInflater.from(context)
    val SMALL_VIEW_TYPE = 1
    val LARGE_VIEW_TYPE = 2

    override fun getItemViewType(position: Int): Int {
        return when(position % 2) {
            1 -> SMALL_VIEW_TYPE
            else -> LARGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return when(viewType) {
            SMALL_VIEW_TYPE -> ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
            else -> ViewHolder(inflater.inflate(R.layout.list_item_large, parent, false))
        }.apply {
            this.itemView.setOnClickListener {
                Snackbar.make(this.itemView, "item: $adapterPosition", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view) as TextView
    }
}

