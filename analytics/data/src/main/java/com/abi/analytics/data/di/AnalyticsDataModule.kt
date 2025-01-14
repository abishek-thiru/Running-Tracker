package com.abi.analytics.data.di

import com.abi.analytics.data.RoomAnalyticsRepository
import com.abi.analytics.domain.AnalyticsRepository
import com.abi.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}