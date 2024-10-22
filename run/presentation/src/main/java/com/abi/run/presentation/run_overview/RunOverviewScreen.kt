package com.abi.run.presentation.run_overview

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abi.core.presentation.designsystem.AnalyticsIcon
import com.abi.core.presentation.designsystem.LogoIcon
import com.abi.core.presentation.designsystem.RunIcon
import com.abi.core.presentation.designsystem.RunningTrackerTheme
import com.abi.core.presentation.designsystem.components.RunningTrackerFloatingActionButton
import com.abi.core.presentation.designsystem.components.RunningTrackerScaffold
import com.abi.core.presentation.designsystem.components.RunningTrackerToolbar
import com.abi.core.presentation.designsystem.components.util.DropDownItem
import com.abi.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        onAction = viewModel::onAction
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    RunningTrackerScaffold(
        topAppBar = {
            RunningTrackerToolbar(
                modifier = Modifier.padding(top = 20.dp),
                showBackButton = false,
                title = stringResource(R.string.running_tracker),
                scrollBehavior = scrollBehavior,
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoIcon,
                        title = stringResource(R.string.logout)
                    )
                ),
                onMenuItemClick = {index ->
                    when(index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                }
            )

        },
        floatingActionButton = {
            RunningTrackerFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) {

    }
}

@Preview
@Composable
private fun RunOverViewScreenPreview() {

    RunningTrackerTheme {
        RunOverviewScreen {  }
    }
    
}