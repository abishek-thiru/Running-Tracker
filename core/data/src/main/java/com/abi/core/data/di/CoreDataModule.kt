package com.abi.core.data.di

import com.abi.core.data.auth.EncryptedSessionStorage
import com.abi.core.data.networking.HttpClientFactory
import com.abi.core.data.run.OfflineFirstRunRepository
import com.abi.core.domain.SessionStorage
import com.abi.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}