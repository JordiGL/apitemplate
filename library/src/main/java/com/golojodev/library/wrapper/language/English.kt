package com.golojodev.library.wrapper.language

import kotlinx.serialization.Serializable

@Serializable
data class English(
    override val name: String = "english",
    override val locale: String = "en"
) : Language()