package com.abi.run.presentation.run_overview.model

import com.abi.core.domain.location.Location
import java.time.ZonedDateTime
import kotlin.time.Duration

data class RunUi(
    val id: String,
    val duration: String,
    val dateTime: String,
    val distance: String,
    val avgSpeed: String,
    val maxSpeed: String,
    val pace: String,
    val totalElevation: String,
    val mapPictureUrl: String?
)
