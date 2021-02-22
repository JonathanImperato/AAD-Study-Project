package com.macoev.roomsample

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindings {
    @JvmStatic
    @BindingAdapter("adapter")
    fun <VH : RecyclerView.ViewHolder> adapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<VH>) {
        recyclerView.adapter = adapter
    }
}