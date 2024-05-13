package com.golojodev.apitemplate.presentation.screens.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.golojodev.apitemplate.domain.models.Model

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModelListItem(
    model: Model,
    onFavoriteClicked: (Model) -> Unit,
    onClicked: (Model) -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .testTag("ModelListItemCard")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
                    onClicked(model)
                }
                .testTag("ModelListItemColumn")
        ) {
            Text(text = model.id.toString())
            Text(text = model.name)
            FlowRow(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
            ) {
                repeat(model.tags.size) {
                    SuggestionChip(
                        modifier = Modifier
                            .padding(
                                start = 3.dp,
                                end =
                                3.dp
                            ),
                        onClick = { },
                        label = {
                            Text(text = model.tags[it])
                        }
                    )
                }
            }
            Icon(
                modifier = Modifier
                    .testTag("ModelListItemFavoriteIcon")
                    .clickable {
                        onFavoriteClicked(model.copy(isFavorite = !model.isFavorite))
                    },
                imageVector = if (model.isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Favorite",
                tint = if (model.isFavorite) {
                    Color.Red
                } else {
                    Color.Gray
                }
            )
        }
    }
}