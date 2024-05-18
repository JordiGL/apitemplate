package com.golojodev.timecontrol

import java.util.Date
import kotlinx.coroutines.flow.MutableStateFlow

object TimeControlHandler {

    private val timeThreshold = MutableStateFlow(0L)

    fun init() {
        timeThreshold.value = Date().time
    }

    fun hasTimePassed(minutes: Int): Boolean {
        val currentTime = Date().time
        val differenceInMillis = currentTime - timeThreshold.value
        if (differenceInMillis >= minutes * 60 * 1000L) {
            timeThreshold.value = currentTime
            return true
        } else {
            return false
        }
    }
}