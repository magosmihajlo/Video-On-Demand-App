package com.example.data.remote.dto

import com.example.domain.model.Auid
import com.google.gson.annotations.SerializedName

data class AuidDto(
    @SerializedName("value") val valueString: String
)

fun AuidDto.mapToDomain(): Auid {
    return Auid(value = valueString)
}
