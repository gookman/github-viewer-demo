package dk.bluebox.demo.githubviewer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}