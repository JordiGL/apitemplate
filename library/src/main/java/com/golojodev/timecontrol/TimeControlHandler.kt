package com.golojodev.timecontrol

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import kotlin.time.Duration

object TimeControlHandler {

    private val timeThreshold = MutableStateFlow(0L)

    fun init() {
        timeThreshold.value = Date().time
    }

    fun hasTimePassed(duration: Duration): Boolean {
        val currentTime = Date().time
        val differenceInMillis = currentTime - timeThreshold.value
        if (differenceInMillis >= duration.inWholeMilliseconds) {
            timeThreshold.value = currentTime
            return true
        } else {
            return false
        }
    }
}