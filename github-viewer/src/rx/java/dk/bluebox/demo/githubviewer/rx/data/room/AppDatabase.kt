package dk.bluebox.demo.githubviewer.rx.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dk.bluebox.demo.githubviewer.common.data.room.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}