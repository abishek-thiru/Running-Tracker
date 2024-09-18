package com.abi.auth.domain

import com.abi.core.domain.util.DataError
import com.abi.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}