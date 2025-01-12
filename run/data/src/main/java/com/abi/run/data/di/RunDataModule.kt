package com.abi.run.data.di

import com.abi.run.data.CreateRunWorker
import com.abi.run.data.DeleteRunWorker
import com.abi.run.data.FetchRunWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunWorker)
    workerOf(::DeleteRunWorker)
}