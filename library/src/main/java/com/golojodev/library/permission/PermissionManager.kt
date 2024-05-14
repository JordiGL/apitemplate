package com.golojodev.library.permission

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionManager {

    fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
        return (
            ContextCompat.checkSelfPermission(context, permission)
                == android.content.pm.PackageManager.PERMISSION_GRANTED
            )
    }

    fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {
        val activity = context as Activity?
        if (activity == null) Log.d("Permissions", "Activity is null")
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity!!,
            permission
        )
    }
}