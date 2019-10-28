package dk.bluebox.demo.githubviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dk.bluebox.demo.githubviewer.databinding.ActivityMainBinding
import dk.bluebox.demo.githubviewer.ui.utils.activityLayoutBinding
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    private val binding by activityLayoutBinding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoin()

        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)

            modules(listOf(koinModule, getActivityModule()))
        }
    }

    private fun getActivityModule(): Module {
        return module { single { this@MainActivity as AppCompatActivity } }
    }
}
