package com.golojodev.library.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

/**
 * Obte la activitat segons el Context
 */
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Gestiona l'execució d'un `Intent`.
 *
 * @param intent L'Intent que s'ha d'executar.
 * @param tag Una etiqueta per a la gestió d'errors.
 */
fun Context.handleIntent(intent: Intent, tag: String) {
    try {
        startActivity(intent)
    } catch (e: Exception) {
        Log.e(tag, "Error: ${e.message}")
    }
}

/**
 * Mostra un Snackbar amb la versió de l'app
 */
suspend fun Context.showAppVersion(snackbarHostState: SnackbarHostState) {
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    val versionName = packageInfo.versionName ?: "N/D"
    snackbarHostState.showSnackbar(
        message = "v:$versionName",
        duration = SnackbarDuration.Short
    )
}