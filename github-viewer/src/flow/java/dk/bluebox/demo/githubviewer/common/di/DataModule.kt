package dk.bluebox.demo.githubviewer.common.di

import dagger.Module
import dagger.Provides
import dk.bluebox.demo.githubviewer.common.data.DefaultGitHubRepository
import dk.bluebox.demo.githubviewer.common.data.GitHubRepository
import dk.bluebox.demo.githubviewer.common.data.room.AppDatabase
import dk.bluebox.demo.githubviewer.common.data.room.RoomGitHubRepository
import dk.bluebox.demo.githubviewer.common.network.GitHubApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Named
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Module
class DataModule {
    @Singleton
    @Named(DataModuleConstants.DEFAULT_REPOSITORY)
    @Provides
    fun providesDefaultGithubRepository(api: GitHubApi): GitHubRepository {
        return DefaultGitHubRepository(api)
    }

    @Singleton
    @Named(DataModuleConstants.ROOM_REPOSITORY)
    @Provides
    fun providesRoomRepository(
        @Named(DataModuleConstants.DEFAULT_REPOSITORY) repository: GitHubRepository,
        appDatabase: AppDatabase
    ): GitHubRepository {
        return if (repository is DefaultGitHubRepository) {
            RoomGitHubRepository(repository, appDatabase)
        } else {
            error("Room dependency repository not correct ${repository::class.simpleName}")
        }
    }
}
