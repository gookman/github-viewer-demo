package dk.bluebox.demo.githubviewer.common.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dk.bluebox.demo.githubviewer.ViewerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    DatabaseModule::class,
    DataModule::class,
    ReactiveModule::class,
    NetworkModule::class,
    MainActivityModule::class
])
interface ViewerApplicationComponent {
    fun inject(application: ViewerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ViewerApplication): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ViewerApplicationComponent
    }
}
