package com.golojodev.apitemplate.presentation.screens.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.golojodev.apitemplate.domain.models.Model

@Composable
fun ModelListItem(
    model: Model,
    onClicked: (Model) -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable{
                    onClicked(model)
                }
        ) {
            Text(text = model.id.toString())
            Text(text = model.name)
        }
    }
}