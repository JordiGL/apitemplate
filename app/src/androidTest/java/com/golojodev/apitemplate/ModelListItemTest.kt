package com.golojodev.apitemplate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.presentation.screens.content.ModelListItem
import org.junit.Rule
import org.junit.Test

class ModelListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testModelListItem() {
        with(composeTestRule) {
            setContent {
                ModelListItem(
                    model = Model(
                        id = 1,
                        name = "John Doe",
                        tags = listOf("cute", "fluffy"),
                        isFavorite = false
                    ),
                    onClicked = { },
                    onFavoriteClicked = {}
                )
            }

            onNodeWithTag("ModelListItemCard").assertExists()
            onNodeWithTag("ModelListItemColumn").assertExists()
            onNodeWithTag("ModelListItemFavoriteIcon").assertExists()

            onNodeWithText("fluffy").assertIsDisplayed()
            onNodeWithContentDescription("Favorite").assertIsDisplayed()

            onNodeWithTag("ModelListItemFavoriteIcon").performClick()
        }
    }
}