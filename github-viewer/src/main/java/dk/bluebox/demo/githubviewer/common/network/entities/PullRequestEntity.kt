package dk.bluebox.demo.githubviewer.common.network.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class PullRequestEntity(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "number") val number: Long,
    @Json(name = "state") val state: String,
    @Json(name = "user") val owner: OwnerEntity,
    @Json(name = "updated_at") val lastUpdate: LocalDateTime
)