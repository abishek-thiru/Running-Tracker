package com.abi.run.presentation.active_run.maps

import androidx.compose.ui.graphics.Color
import com.abi.core.domain.location.Location

data class PolyLineUi(
    val location1: Location,
    val location2: Location,
    val color: Color
)
