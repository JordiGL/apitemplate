package com.golojodev.apitemplate.core.permissions

sealed class PermissionAction {
    data object PermissionGranted : PermissionAction()
    data object PermissionDenied : PermissionAction()
}