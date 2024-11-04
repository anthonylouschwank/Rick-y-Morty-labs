package com.example.lab10.data.ktor

import kotlinx.serialization.Serializable
import com.example.lab10.data.model.Location

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

@Serializable
data class LocationListDto(
    val results: List<LocationDto>
)

fun LocationDto.mapToLocationModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}