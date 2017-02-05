package com.sakebook.android.sample.bottomnavigationviewsample

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by sakemotoshinya on 2017/02/06.
 */
class MainAdapter(val context: Context, val items: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.list_item, null))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.textView.text = items[position]
        val bgColor = if (position % 2 == 0) R.color.white else R.color.gray
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, bgColor))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view) as TextView
    }
}

