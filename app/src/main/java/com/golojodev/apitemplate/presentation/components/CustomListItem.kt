package com.golojodev.apitemplate.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.golojodev.apitemplate.ui.theme.ApitemplateTheme

@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.onSecondary,
    headlineText: @Composable () -> Unit = {},
    supportingText: @Composable (() -> Unit)? = null,
    overlineContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier.clickable {
            onClick()
        },
        colors = ListItemDefaults.colors(
            containerColor = containerColor
        ),
        leadingContent = leadingContent,
        headlineContent = headlineText,
        overlineContent = overlineContent,
        supportingContent = supportingText,
        trailingContent = trailingContent
    )
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomListItemPreview() {
    ApitemplateTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
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
}