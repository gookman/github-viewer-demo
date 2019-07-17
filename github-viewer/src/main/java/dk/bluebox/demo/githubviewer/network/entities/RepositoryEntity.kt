package dk.bluebox.demo.githubviewer.network.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class RepositoryEntity(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String,
    @Json(name = "stargazers_count") val starsCount: Long,
    @Json(name = "forks_count") val forksCount: Long,
    @Json(name = "watchers_count") val watchersCount: Long,
    @Json(name = "open_issues") val openIssuesCount: Long,
    @Json(name = "owner") val owner: OwnerEntity,
    @Json(name = "updated_at") val lastUpdate: LocalDateTime,
    @Json(name = "pulls_url") val pullsUrl: String,
    @Json(name = "language") val language: String?
)