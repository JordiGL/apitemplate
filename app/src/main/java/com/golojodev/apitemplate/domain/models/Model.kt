package com.golojodev.apitemplate.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Model(
    @SerialName("_id")
    val id: Int,
    @SerialName("title")
    val name: String,
    @SerialName("tags")
    val tags: List<String>,
    val isFavorite: Boolean = false
)