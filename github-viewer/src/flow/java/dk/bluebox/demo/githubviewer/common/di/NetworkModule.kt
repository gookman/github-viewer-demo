package dk.bluebox.demo.githubviewer.common.di

import dagger.Module
import dagger.Provides
import dk.bluebox.demo.githubviewer.common.network.GitHubApi
import dk.bluebox.demo.githubviewer.common.network.GitHubApiImpl
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory
import dk.bluebox.demo.githubviewer.common.network.ServiceFactoryImpl
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesServiceFactory(): ServiceFactory {
        return ServiceFactoryImpl()
    }

    @Singleton
    @Provides
    fun providesGitHubApi(serviceFactory: ServiceFactory): GitHubApi {
        return GitHubApiImpl(serviceFactory)
    }
}
