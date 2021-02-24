package com.macoev.aadstudyproject.utils

import androidx.lifecycle.Observer

open class Event<out T>(private val content: T?) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getUnhandledContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun getContent(): T? = content
}

open class EventObserver<T>(private val onEventChanged: (T) -> Unit) : Observer<Event<T?>?> {
    override fun onChanged(t: Event<T?>?) {
        t?.getUnhandledContent()?.let(onEventChanged)
    }
}