package com.golojodev.library.wrapper.language

import kotlinx.serialization.Serializable

@Serializable
data class Catala(
    override val name: String = "català",
    override val locale: String = "ca"
) : Language()