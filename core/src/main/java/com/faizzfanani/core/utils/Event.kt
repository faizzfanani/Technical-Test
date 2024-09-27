package com.faizzfanani.core.utils

class Event<T>(private val content: T) {
    var hasBeenHandle = false
        private set

    val contentIfNotHaveBeenHandle: T?
        get() {
            return if (hasBeenHandle) {
                null
            } else {
                hasBeenHandle = true
                content
            }
        }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}