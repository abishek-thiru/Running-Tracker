package com.abi.run.presentation.run_overview

import com.abi.run.presentation.run_overview.model.RunUi

sealed interface RunOverviewAction {
    data object OnStartClick: RunOverviewAction
    data object OnLogoutClick: RunOverviewAction
    data object OnAnalyticsClick: RunOverviewAction
    data class DeleteRun(val runui: RunUi): RunOverviewAction
}