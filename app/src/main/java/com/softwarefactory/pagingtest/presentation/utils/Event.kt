package com.softwarefactory.pagingtest.presentation.utils
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun handle(action: (T) -> Unit) {
        if (!hasBeenHandled) {
            action(content)
            hasBeenHandled = true
        }
    }

    fun peek(): T = content
}