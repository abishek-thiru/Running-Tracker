package com.abi.runningtracker

import android.app.Application
import com.abi.auth.data.di.authDataModule
import com.abi.auth.presentation.di.authViewModelModule
import com.abi.core.data.di.coreDataModule
import com.abi.core.database.di.databaseModule
import com.abi.run.data.di.runDataModule
import com.abi.run.location.di.locationModule
import com.abi.run.network.di.networkModule
import com.abi.run.presentation.di.runPresentationModule
import com.abi.runningtracker.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class RunningTrackerApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunningTrackerApp)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule
            )
        }
    }
}