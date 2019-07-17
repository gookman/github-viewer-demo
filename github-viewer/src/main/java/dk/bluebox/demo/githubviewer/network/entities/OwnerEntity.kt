package dk.bluebox.demo.githubviewer.network.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerEntity(
    @Json(name = "id") val id: Long,
    @Json(name = "login") val name: String
)