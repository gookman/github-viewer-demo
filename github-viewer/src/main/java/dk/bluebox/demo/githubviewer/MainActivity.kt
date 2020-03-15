package dk.bluebox.demo.githubviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dk.bluebox.demo.githubviewer.common.ui.core.databinding.activityLayoutBinding
import dk.bluebox.demo.githubviewer.databinding.ActivityMainBinding
import dk.bluebox.demo.githubviewer.common.ui.core.databinding.activityLayoutBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding by activityLayoutBinding<ActivityMainBinding>(
        R.layout.activity_main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
