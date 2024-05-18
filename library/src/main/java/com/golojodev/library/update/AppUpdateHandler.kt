package com.golojodev.library.update

import android.content.Context
import android.widget.Toast
import com.golojodev.library.utils.findActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Objecte que gestiona la comprobació i descàrrega d'actualitzacions de l'aplicació.
 */
object AppUpdateHandler {
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var updateListener: InstallStateUpdatedListener

    /**
     * Inicialitza el gestor d'actualitzacions de l'aplicació.
     *
     * @param context El context de l'aplicació actual.
     */
    fun initialize(context: Context) {
        setupAppUpdateManager(context)
    }

    /**
     * Configura el gestor d'actualitzacions de l'aplicació i comprova si hi ha actualitzacions disponibles.
     *
     * @param context El context de l'aplicació actual.
     */
    private fun setupAppUpdateManager(context: Context) {
        appUpdateManager = AppUpdateManagerFactory.create(context)
        updateListener = InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.DOWNLOADED -> showDownloadedToast(context)
                InstallStatus.DOWNLOADING -> showDownloadingToast(context)
                else -> {}
            }
        }
        appUpdateManager.registerListener(updateListener)
        checkForUpdate(context)
    }

    /**
     * Comprova si hi ha actualitzacions disponibles per a l'aplicació.
     *
     * @param context El context de l'aplicació actual.
     * @param notifyIfNoPendingUpdate Un valor boolean que indica si s'ha de mostrar un missatge
     * si no hi ha actualitzacions pendents (opcional).
     */
    fun checkForUpdate(
        context: Context,
        notifyIfNoPendingUpdate: Boolean = false,
        text: String = "No pending updates"
    ) {
        context.findActivity()?.let {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        it,
                        AppUpdateOptions.defaultOptions(AppUpdateType.FLEXIBLE),
                        123
                    )
                } else {
                    if (notifyIfNoPendingUpdate) {
                        Toast.makeText(
                            context,
                            text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * Desregistra l'escoltador d'actualitzacions.
     */
    fun unregisterListener() {
        appUpdateManager.unregisterListener(updateListener)
    }

    /**
     * Comprova si l'actualització està descarregada.
     *
     * @param context El context de l'aplicació actual.
     */
    fun checkIfUpdateDownloaded(context: Context) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showDownloadedToast(context)
            }
        }
    }

    /**
     * Mostra un missatge emergent indicant que l'actualització està descarregada i reinicia l'aplicació.
     *
     * @param context El context de l'aplicació actual.
     */
    private fun showDownloadedToast(
        context: Context,
        text: String = "The APP will restart in 5 seconds"
    ) {
        val scope = CoroutineScope(SupervisorJob())
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
        scope.launch {
            delay(5.seconds)
            appUpdateManager.completeUpdate()
        }
    }

    /**
     * Mostra un missatge emergent indicant que l'actualització s'està descarregant.
     *
     * @param context El context de l'aplicació actual.
     */
    private fun showDownloadingToast(
        context: Context,
        text: String = "Downloading update"
    ) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}