package dk.bluebox.demo.githubviewer

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen

class ViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }
}
