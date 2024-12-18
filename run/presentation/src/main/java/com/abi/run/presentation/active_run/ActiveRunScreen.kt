package com.abi.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abi.core.presentation.designsystem.RunningTrackerTheme
import com.abi.core.presentation.designsystem.StartIcon
import com.abi.core.presentation.designsystem.StopIcon
import com.abi.core.presentation.designsystem.components.AppOutlinedActionButton
import com.abi.core.presentation.designsystem.components.RunningTrackerDialog
import com.abi.core.presentation.designsystem.components.RunningTrackerFloatingActionButton
import com.abi.core.presentation.designsystem.components.RunningTrackerScaffold
import com.abi.core.presentation.designsystem.components.RunningTrackerToolbar
import com.abi.run.presentation.R
import com.abi.run.presentation.active_run.components.RunDataCard
import com.abi.run.presentation.util.hasLocationPermission
import com.abi.run.presentation.util.hasNotificationPermission
import com.abi.run.presentation.util.shouldShowLocationPermissionRationale
import com.abi.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCoarseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCoarseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationRationale = showNotificationRationale
            )
        )

    }

    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationRationale = showNotificationRationale
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
             permissionLauncher.requestRunningTrackerPermission(context)
        }
    }
    RunningTrackerScaffold(
        withGradient = false,
        topAppBar = {
            RunningTrackerToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            RunningTrackerFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(R.string.pause_run)
                } else {
                    stringResource(R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }

    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        RunningTrackerDialog(
            title = stringResource(R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed*/ },
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(R.string.location_notification_rationale)
                }
                state.showLocationRationale -> {
                    stringResource(R.string.location_rationale)
                }
                else -> {
                    stringResource(R.string.notification_rationale)
                }
            },
            primaryButton = {
                AppOutlinedActionButton(
                    text = stringResource(R.string.ok),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.DismissRationaleDialog)
                        permissionLauncher.requestRunningTrackerPermission(context)
                    }
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRunningTrackerPermission(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()
    val locationPermission = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()
    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermission + notificationPermission)
        }
        !hasLocationPermission -> launch(locationPermission)
        !hasNotificationPermission -> launch(notificationPermission)
    }

}

@Preview
@Composable
private fun ActiveRunScreenPreview() {

    RunningTrackerTheme {
        ActiveRunScreen(
            state = ActiveRunState()
        ) {

        }
    }

}