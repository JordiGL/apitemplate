package com.golojodev.library.wrapper.language

import kotlinx.serialization.Serializable

@Serializable
data class Espanol(
    override val name: String = "español",
    override val locale: String = "es"
) : Language()