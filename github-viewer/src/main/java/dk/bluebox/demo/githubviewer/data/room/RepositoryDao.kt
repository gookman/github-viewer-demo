package dk.bluebox.demo.githubviewer.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository WHERE id LIKE :id")
    fun findById(id: Long): Single<RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: RepositoryEntity): Completable
}