package com.abi.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick: AnalyticsAction
}