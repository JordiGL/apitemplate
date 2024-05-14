package com.golojodev.library.wrapper.language

import java.util.Locale
import kotlinx.serialization.Serializable

/**
 * Idiomes que suportarà l'APP
 */
@Serializable
sealed class Language {
    abstract val name: String
    abstract val locale: String

    /**
     * Crea un [Locale] a partir de l'String que defineix l'dioma en format locale
     * @return [Locale] de l'idioma
     */
    fun toLocale() = Locale(locale)

    companion object {
        val DEVICE: Language = getDeviceLanguage()
        private fun getDeviceLanguage(): Language {
            return when (Locale.getDefault().displayLanguage) {
                Catala().name -> Catala()
                Espanol().name -> Espanol()
                English().name -> English()
                else -> English()
            }
        }
    }
}