package com.example.data.remote.dto

import com.google.gson.*
import java.lang.reflect.Type

class VodDtoDeserializer : JsonDeserializer<AuidDto> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): AuidDto {
        val rawValue = json.asString
        return AuidDto(valueString = rawValue)
    }
}
