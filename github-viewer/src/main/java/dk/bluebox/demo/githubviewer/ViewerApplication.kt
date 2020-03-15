package dk.bluebox.demo.githubviewer

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dk.bluebox.demo.githubviewer.common.di.DaggerViewerApplicationComponent
import javax.inject.Inject

class ViewerApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        DaggerViewerApplicationComponent.builder()
            .application(this)
            .context(this)
            .build()
            .inject(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}
