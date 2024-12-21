package com.abi.run.domain

import com.abi.core.domain.location.LocationTimeStamp
import kotlin.math.roundToInt

object LocationDataCalculator {

    fun getTotalDistanceInMeters(locations: List<List<LocationTimeStamp>>): Int {
        return locations
            .sumOf { timeStampsPerLine ->
                timeStampsPerLine.zipWithNext { location1, location2 ->
                    location1.location.location.distanceTo(location2.location.location)
                }.sum().roundToInt()
            }
    }

}