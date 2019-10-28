package dk.bluebox.demo.githubviewer.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class RepositoryEntity(
    @PrimaryKey val id: Long,
    val bookmarked: Boolean
)