package com.abi.run.domain

import com.abi.core.domain.location.LocationTimeStamp
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

object LocationDataCalculator {

    fun getTotalDistanceInMeters(locations: List<List<LocationTimeStamp>>): Int {
        return locations
            .sumOf { timeStampsPerLine ->
                timeStampsPerLine.zipWithNext { location1, location2 ->
                    location1.location.location.distanceTo(location2.location.location)
                }.sum().roundToInt()
            }
    }

    fun getMaxSpeedKmh(locations: List<List<LocationTimeStamp>>): Double {
        return locations.maxOf { locationSet ->
            locationSet.zipWithNext { location1, location2 ->
                val distance = location1.location.location.distanceTo(
                    other = location2.location.location
                )
                val hoursDiff = (location2.durationTimeStamp - location1.durationTimeStamp)
                    .toDouble(DurationUnit.HOURS)
                if (hoursDiff == 0.0) {
                    return 0.0
                } else {
                    (distance / 1000) / hoursDiff
                }
            }.maxOrNull() ?: 0.0
        }
    }

    fun getTotalElevationMeters(locations: List<List<LocationTimeStamp>>): Int {
        return locations.sumOf { locationSet ->
            locationSet.zipWithNext{ location1, location2 ->
                val altitude1 = location1.location.altitude
                val altitude2 = location2.location.altitude
                (altitude2 - altitude1).coerceAtLeast(0.0)
            }.sum().roundToInt()
        }
    }

}