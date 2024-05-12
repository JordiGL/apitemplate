package com.golojodev.apitemplate.presentation.navigation

sealed interface ContentType {
    object List : ContentType
    object ListAndDetail : ContentType
}