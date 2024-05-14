package com.golojodev.library.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

/**
 * Gestiona la creació i la visualització de notificacions.
 */
class NotificationHandler(
    private val context: Context,
    private val notificationManager: NotificationManager
) {

    /**
     * Mostra una notificació amb el títol i el cos especificats.
     *
     * @param channel Canal de notificació a utilitzar.
     * @param title Títol de la notificació.
     * @param body Cos de la notificació.
     */
    fun showNotification(
        channel: NotificationChannel,
        title: String,
        body: String,
        icon: Int
    ) {
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channel.id)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0, notification)
    }
}