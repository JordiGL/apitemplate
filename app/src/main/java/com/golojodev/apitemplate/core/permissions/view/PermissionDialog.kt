package com.golojodev.apitemplate.core.permissions.view

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.golojodev.apitemplate.core.permissions.PermissionAction
import com.golojodev.apitemplate.core.permissions.PermissionManager.checkIfPermissionGranted
import com.golojodev.apitemplate.core.permissions.PermissionManager.shouldShowPermissionRationale

@Composable
fun PermissionDialog(
    context: Context,
    title: String = "Permission Required",
    text: String = "This app requires the location permission to be granted.",
    confirmText: String = "Grant Access",
    dismissText: String = "Cancel",
    permission: String,
    permissionAction: (PermissionAction) -> Unit
) {
    val isPermissionGranted = checkIfPermissionGranted(context, permission)
    if (isPermissionGranted) {
        permissionAction(PermissionAction.PermissionGranted)
        return
    }
    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permissionAction(PermissionAction.PermissionGranted)
        } else {
            permissionAction(PermissionAction.PermissionDenied)
        }
    }
    val showPermissionRationale = shouldShowPermissionRationale(context, permission)
    var isDialogDismissed by remember { mutableStateOf(false) }
    var isPristine by remember { mutableStateOf(true) }

    if ((showPermissionRationale && !isDialogDismissed) || (!isDialogDismissed && !isPristine)) {
        isPristine = false
        AlertDialog(
            onDismissRequest = {
                isDialogDismissed = true
                permissionAction(PermissionAction.
                PermissionDenied)
            },
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionsLauncher.launch(permission)
                    }
                ) {
                    Text(text = confirmText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionAction(PermissionAction.
                        PermissionDenied)
                    }
                ) {
                    Text(text = dismissText)
                }
            }
        )
    } else {
        if (!isDialogDismissed) {
            SideEffect {
                permissionsLauncher.launch(permission)
            }
        }
    }
}