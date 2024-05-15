package com.golojodev.apitemplate.presentation.screens.content

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.golojodev.apitemplate.presentation.components.CustomListItem

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    theme: String = "Dark",
    onThemeClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = ""
                )
            },
            headlineText = {
                Text(text = "Tema")
            },
            supportingText = {
                Text(text = theme)
            }
        ) {
            onThemeClick()
        }
        CustomListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = ""
                )
            },
            headlineText = {
                Text(text = "Title")
            },
            supportingText = {
                Text(text = "Subtitle")
            }
        )
        CustomListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = ""
                )
            },
            headlineText = {
                Text(text = "Title")
            },
            supportingText = {
                Text(text = "Subtitle")
            }
        )
        CustomListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = ""
                )
            },
            headlineText = {
                Text(text = "Title")
            },
            supportingText = {
                Text(text = "Subtitle")
            }
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomListItemPreview() {
    Surface(color = MaterialTheme.colorScheme.background) {
        SettingsScreenContent()
    }
}