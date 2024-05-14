package com.golojodev.library.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import com.golojodev.library.utils.handleIntent

/**
 * Facilita la creació i gestió d'Intents a Android.
 */
object IntentManager {

    /**
     * Crea un `Intent` amb els paràmetres especificats.
     *
     * @param action L'acció que l'Intent ha d'executar.
     * @param data Les dades que l'Intent ha de processar.
     * @param type El tipus de dades que l'Intent ha de processar.
     * @param setPackage El paquet de l'aplicació que ha d'obrir l'Intent.
     * @param extras Un conjunt de dades addicionals per a l'Intent.
     *
     * @return Un `Intent` configurat amb els paràmetres especificats.
     */
    private fun createIntent(
        action: String,
        data: Uri? = null,
        type: String? = null,
        setPackage: String? = null,
        extras: Bundle? = null
    ): Intent {
        return Intent(action).apply {
            data?.let { this.data = it }
            extras?.let { this.putExtras(it) }
            type?.let { this.type = it }
            setPackage?.let { this.setPackage(it) }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * Genera un `Intent` per enviar un correu electrònic quan s'executa.
     * Està configurat per enviar un correu electrònic a l'adreça especificada amb l'assumpte del correu electrònic establert.
     *
     * @exception Exception Si no hi ha cap aplicació instal·lada en el dispositiu que pugui gestionar aquest `Intent`
     */
    fun createEmail(context: Context, emails: Array<String>, subject: String) {
        val intent = createIntent(
            action = Intent.ACTION_SEND,
            type = "text/plain",
            data = null,
            extras = Bundle().apply {
                putStringArray(Intent.EXTRA_EMAIL, emails)
                putString(Intent.EXTRA_SUBJECT, subject)
            }
        )
        context.handleIntent(intent, "createEmail")
    }

    /**
     * Genera un `Intent` per obrir el perfil de Google Play quan s'executa.
     * Està configurat per obrir el perfil de Google Play amb l'ID especificat.
     *
     * @exception Exception Si no hi ha cap aplicació instal·lada en el dispositiu que pugui gestionar aquest `Intent`
     */
    fun openGooglePlayProfile(context: Context) {
        val intent = createIntent(
            action = Intent.ACTION_VIEW,
            data = Uri.parse("https://play.google.com/store/apps/dev?id=7061416679954621830"),
            setPackage = "com.android.vending"
        )
        context.handleIntent(intent, "openGooglePlayProfile")
    }

    /**
     * Genera un `Intent` per per a crear un event al calendari.
     *
     * @param context Context de l'aplicació
     * @param title titol de l'event
     * @param description descripció de l'event
     * @exception Exception Si no hi ha cap aplicació instal·lada en el dispositiu que pugui gestionar aquest `Intent`
     */
    fun createEvent(
        context: Context,
        title: String,
        description: String
    ) {
        val intent = createIntent(
            action = Intent.ACTION_INSERT,
            type = "vnd.android.cursor.dir/event",
            data = CalendarContract.Events.CONTENT_URI,
            extras = Bundle().apply {
                putString(CalendarContract.Events.TITLE, title)
                putString(CalendarContract.Events.DESCRIPTION, description)
            }
        )
        context.handleIntent(intent, "createEvent")
    }

    /**
     * Genera un `Intent` per per a compartir informació.
     *
     * @param context Context de l'aplicació
     * @param data informació a compartir
     * @exception Exception Si no hi ha cap aplicació instal·lada en el dispositiu que pugui gestionar aquest `Intent`
     */
    fun shareData(context: Context, data: String) {
        val intent = createIntent(
            action = Intent.ACTION_SEND,
            type = "text/plain",
            extras = Bundle().apply {
                putString(Intent.EXTRA_TEXT, data)
            }
        )
        context.handleIntent(intent, "share")
    }
}