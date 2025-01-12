package com.abi.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.abi.core.database.dao.RunPendingSyncDao
import com.abi.core.database.mappers.toRun
import com.abi.core.domain.run.RemoteRunDataSource
import com.abi.core.domain.util.Result

class CreateRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val pendingRunId = params.inputData.getString(RUN_ID) ?: return Result.failure()
        val pendingRunEntity = pendingSyncDao.getRunPendingSyncEntity(pendingRunId)
            ?: return Result.failure()

        val run = pendingRunEntity.run.toRun()
        return when(val result = remoteRunDataSource.postRun(run, pendingRunEntity.mapPictureBytes)) {
            is com.abi.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
            is com.abi.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteRunPendingSyncEntity(pendingRunId)
                Result.success()
            }
        }
    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }
}