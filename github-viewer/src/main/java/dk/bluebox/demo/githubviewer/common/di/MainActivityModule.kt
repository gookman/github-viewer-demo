package dk.bluebox.demo.githubviewer.common.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dk.bluebox.demo.githubviewer.MainActivity
import dk.bluebox.demo.githubviewer.common.navigation.Router
import dk.bluebox.demo.githubviewer.common.navigation.RouterImpl

@Module
abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [InnerModule::class, FeaturesModule::class])
    abstract fun activity(): MainActivity

    @Module
    class InnerModule {
        @ActivityScope
        @Provides
        fun providesRouter(activity: MainActivity): Router {
            return RouterImpl(activity)
        }
    }
}
