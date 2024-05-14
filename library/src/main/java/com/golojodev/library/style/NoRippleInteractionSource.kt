package com.golojodev.library.style

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Elimina l'indicador del clickable que apareix quan ha estat premut
 * Normalment el component canvia a un color grisenc (creant un efecte) durant un segons
 * quan l'apretes, amb aquesta classe aconseguim que no apareixi cap efecte
 *
 */
class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}