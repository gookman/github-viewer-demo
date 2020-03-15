package dk.bluebox.demo.githubviewer.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository WHERE id LIKE :id")
    suspend fun findById(id: Long): RepositoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: RepositoryEntity)
}