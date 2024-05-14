package com.golojodev.library.review

import android.app.Activity
import android.util.Log
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * Gestiona el procés de revisió de l'aplicació.
 *
 */
object AppReviewHandler {

    /**
     * Inicia el procés de revisió de l'aplicació dins d'una activitat.
     *
     * @param activity L'activitat on mostrar el procés de revisió.
     * @param reviewManager El gestor de revisió proporcionat per ReviewManagerFactory.
     */
    private fun startReviewFlow(activity: Activity, reviewManager: ReviewManager) {
        // Sol·licita el flux de revisió al sistema operatiu.
        reviewManager.requestReviewFlow().addOnCompleteListener { task ->
            // Si la tasca és satisfactòria, inicia el flux de revisió a l'activitat.
            if (task.isSuccessful) {
                reviewManager.launchReviewFlow(activity, task.result)
            } else {
                // Si la tasca falla, imprimeix un missatge d'error indicant el problema.
                Log.e("AppReview", "ReviewFlow task not succesfull: ${task.exception?.message}")
            }
        }
    }

    /**
     * Inicia el procés de revisió si l'activitat proporcionada no és nul·la.
     *
     * @param activity L'activitat actual (pot ser nul·la). Es pot obtenir l’activitat actual
     * a partir del context: Context.findActivity()
     */
    fun launchReviewProcess(activity: Activity?) {
        // Si l'activitat no és nul·la, executa la funció startReviewFlow.
        activity?.let {
            startReviewFlow(it, ReviewManagerFactory.create(it.applicationContext))
        }
    }
}