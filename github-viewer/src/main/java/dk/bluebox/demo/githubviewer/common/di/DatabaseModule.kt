package dk.bluebox.demo.githubviewer.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dk.bluebox.demo.githubviewer.common.data.room.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "github-viewer"
        ).build()
    }
}
