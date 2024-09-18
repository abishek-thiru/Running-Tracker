package com.abi.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}