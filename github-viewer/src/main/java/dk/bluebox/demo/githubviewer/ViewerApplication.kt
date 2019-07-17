package dk.bluebox.demo.githubviewer

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class ViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}