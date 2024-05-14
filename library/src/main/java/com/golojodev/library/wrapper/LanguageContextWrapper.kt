package com.golojodev.library.wrapper

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import com.golojodev.library.wrapper.language.Language
import java.util.Locale

/**
 * A partir del context de l'APP crea un [ContextWrapper] amb l'idioma entrat per parametre
 * @param baseContext el context de l'APP
 * @return ContextWrapper amb el nou idioma configurat
 */
class LanguageContextWrapper(baseContext: Context) : ContextWrapper(baseContext) {
    companion object {

        /**
         * Crea un context amb l'idioma especificat.
         *
         * @param context Context original sobre el qual es crearà el nou context amb l'idioma.
         * @param language Idioma que es vol aplicar al nou context.
         * @return Un ContextWrapper amb l'idioma configurat.
         */
        fun wrap(context: Context, language: Language): ContextWrapper {
            val config = Configuration(context.resources.configuration)
            val newLocale = language.toLocale()
            Locale.setDefault(newLocale)
            config.setLocale(newLocale)

            return LanguageContextWrapper(context.createConfigurationContext(config))
        }
    }
}