package dk.bluebox.demo.githubviewer.common.di

import dagger.Module
import dagger.Provides
import dk.bluebox.demo.githubviewer.common.rx.AndroidSchedulersProvider
import dk.bluebox.demo.githubviewer.common.rx.SchedulersProvider
import javax.inject.Singleton

@Module
class ReactiveModule {
    @Provides
    @Singleton
    fun providesSchedulersProvider(): SchedulersProvider {
        return AndroidSchedulersProvider()
    }
}
