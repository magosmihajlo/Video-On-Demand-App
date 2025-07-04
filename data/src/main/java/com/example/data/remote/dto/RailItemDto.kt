package com.example.data.remote.dto

import com.example.domain.model.RailItem
import com.example.domain.model.VodItem
import com.google.gson.annotations.SerializedName
import android.util.Log

data class RailItemDto(
    @SerializedName("name") val name: String?,
    @SerializedName("items") val items: List<VodItemDto>?
)

fun List<RailItemDto>.toDomain(): List<RailItem> {
    val grouped = mutableMapOf<String, MutableList<VodItem>>()
    val order = mutableListOf<String>()

    for (dto in this) {
        val sourceName = dto.name ?: continue

        val derivedTitle = if (sourceName.startsWith("DRM ", ignoreCase = true)) {
            sourceName.removePrefix("DRM ")
        } else {
            sourceName
        }

        if (derivedTitle.isBlank()) {
            Log.w("DtoMapping", "Skipping blank derived title from source: '$sourceName'")
            continue
        }

        val mappedItems = dto.items?.mapNotNull { it.toDomain() }.orEmpty()
        if (mappedItems.isEmpty()) continue

        if (!grouped.containsKey(derivedTitle)) {
            order.add(derivedTitle)
        }

        grouped.getOrPut(derivedTitle) { mutableListOf() }
            .addAll(mappedItems)
    }

    return order.mapNotNull { title ->
        grouped[title]?.takeIf { it.isNotEmpty() }?.let {
            RailItem(title = title, items = it)
        }
    }
}
