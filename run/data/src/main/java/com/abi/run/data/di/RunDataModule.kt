package com.abi.run.data.di

import com.abi.core.domain.run.SyncRunScheduler
import com.abi.run.data.CreateRunWorker
import com.abi.run.data.DeleteRunWorker
import com.abi.run.data.FetchRunWorker
import com.abi.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}