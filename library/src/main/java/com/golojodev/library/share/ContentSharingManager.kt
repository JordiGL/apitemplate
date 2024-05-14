package com.golojodev.library.share

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Gestiona l'operació de compartir imatges.
 *
 * @param context El context de l'aplicació actual.
 */
class ContentSharingManager(private val context: Context) {

    /**
     * Comparteix una imatge a través d'una aplicació externa.
     *
     * @param bitmap La imatge que es vol compartir.
     * @param chooserTitle El titol a mostrar del selector d'app.
     */
    fun shareImage(
        bitmap: Bitmap,
        chooserTitle: String? = null
    ) {
        val shareFile: suspend (File) -> Unit = { file ->
            val imageUri = FileProvider.getUriForFile(
                context,
                context.packageName,
                file
            )
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, imageUri)
                type = "image/jpeg"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(Intent.createChooser(shareIntent, chooserTitle))
        }

        CoroutineScope(Dispatchers.IO).launch {
            val file = createTemporaryImageFile(bitmap)
            shareFile(file)
        }
    }

    /**
     * Crea un fitxer temporal on s'emmagatzemarà la imatge abans de compartir-la.
     *
     * @param bitmap La imatge que es vol emmagatzemar.
     *
     * @return Un objecte `File` que representa el fitxer temporal creat.
     */
    private suspend fun createTemporaryImageFile(
        bitmap: Bitmap
    ): File = withContext(Dispatchers.IO) {
        val path = context.cacheDir.toString() + "/shared_image.jpg"
        val file = File(path)
        val out: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        file
    }
}