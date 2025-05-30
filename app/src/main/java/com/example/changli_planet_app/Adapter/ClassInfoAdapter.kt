package com.example.changli_planet_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.changli_planet_app.R
import com.example.changli_planet_app.Utils.Event.SelectEvent
import com.example.changli_planet_app.Utils.EventBusHelper

class ClassInfoAdapter(
    val list: List<String>,
    val changeWeek: (String) -> Unit,
    val changeDay: (String) -> Unit,
    val changeRegion: (String) -> Unit
) : RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder>() {

    class ClassInfoViewHolder(item: View) : ViewHolder(item) {
        val select: TextView = item.findViewById(R.id.select)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selector, parent, false)
        return ClassInfoViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ClassInfoViewHolder, position: Int) {
        holder.select.text = list[position]
        holder.select.setOnClickListener {
            // 星期选择
            if (list.size == 7) {
                changeDay(list[position])
                EventBusHelper.post(SelectEvent(1))
            } else if (list.size == 2) {
                // 校区选择
                changeRegion(list[position])
                EventBusHelper.post(SelectEvent(1))
            } else {
                // 周次选择
                changeWeek((position + 1).toString())
                EventBusHelper.post(SelectEvent(1))
            }
        }
    }
}