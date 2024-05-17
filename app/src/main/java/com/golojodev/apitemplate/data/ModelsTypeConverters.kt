package com.golojodev.apitemplate.data

import androidx.room.TypeConverter
import com.golojodev.library.style.ThemeState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ModelsTypeConverters {
    @TypeConverter
    fun convertTagsToString(tags: List<String>): String {
        return Json.encodeToString(tags)
    }

    @TypeConverter
    fun convertStringToTags(tags: String): List<String> {
        return Json.decodeFromString(tags)
    }

    @TypeConverter
    fun convertThemeStateToString(themeState: ThemeState): String {
        return Json.encodeToString(themeState)
    }

    @TypeConverter
    fun convertStringToThemeState(themeState: String): ThemeState {
        return Json.decodeFromString(themeState)
    }
}