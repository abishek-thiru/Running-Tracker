package com.abi.auth.data.di

import com.abi.auth.data.EmailPatternValidator
import com.abi.auth.domain.PatternValidator
import com.abi.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}