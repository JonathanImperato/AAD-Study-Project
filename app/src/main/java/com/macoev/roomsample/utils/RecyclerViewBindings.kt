package com.macoev.roomsample.utils

import android.view.MotionEvent
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindings {
    @JvmStatic
    @BindingAdapter("adapter")
    fun <VH : RecyclerView.ViewHolder> adapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<VH>
    ) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("itemTap")
    fun itemTap(recyclerView: RecyclerView, click: (Int) -> Boolean) {
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                child?.let {
                    val pos = rv.getChildAdapterPosition(it)
                    return click(pos)
                }
                return false
            }

        })
    }
}