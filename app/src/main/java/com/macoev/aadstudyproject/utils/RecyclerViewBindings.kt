package com.macoev.aadstudyproject.utils

import android.view.MotionEvent
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindings {
    @JvmStatic
    @BindingAdapter("adapter")
    fun <VH : RecyclerView.ViewHolder> adapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<VH>) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("itemTap")
    fun itemTap(recyclerView: RecyclerView, click: (RecyclerView.ViewHolder?, Int) -> Boolean) {
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                child?.let {
                    val pos = rv.getChildAdapterPosition(it)
                    val vh = rv.findViewHolderForAdapterPosition(pos)
                    return click(vh, pos)
                }
                return false
            }
        })
    }
}