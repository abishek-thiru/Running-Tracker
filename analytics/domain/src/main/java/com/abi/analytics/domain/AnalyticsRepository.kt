package com.abi.analytics.domain

interface AnalyticsRepository {

    suspend fun getAnalyticsValues(): AnalyticsValues

}