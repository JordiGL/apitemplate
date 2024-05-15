package com.golojodev.apitemplate.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun CustomIconText(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    iconSize: Dp = 17.dp,
    textAlign: TextAlign? = TextAlign.Left,
    fontSize: TextUnit = 12.sp,
    color: Color = LocalContentColor.current,
    fontFamily: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = 0.em,
    lineHeight: TextUnit = 20.15.sp,
    textDecoration: TextDecoration = TextDecoration.None,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onSecondary
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    end = 8.dp
                )
                .align(Alignment.CenterVertically)
                .size(iconSize),
            imageVector = icon,
            contentDescription = text,
            tint = LocalContentColor.current
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = text,
            textAlign = textAlign,
            style = style,
            color = color,
            fontSize = fontSize,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyIconTextPreview() {
    Surface(color = MaterialTheme.colorScheme.background) {
        CustomIconText(
            icon = Icons.Filled.Settings,
            text = "Test"
        )
    }
}