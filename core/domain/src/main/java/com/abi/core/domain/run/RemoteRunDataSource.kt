package com.abi.core.domain.run

import com.abi.core.domain.util.DataError
import com.abi.core.domain.util.EmptyResult
import com.abi.core.domain.util.Result

interface RemoteRunDataSource {

    suspend fun getRuns(): Result<List<Run>, DataError.Network>
    suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network>
    suspend fun deleteRun(id: String): EmptyResult<DataError.Network>
}