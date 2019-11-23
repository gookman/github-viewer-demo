package dk.bluebox.demo.githubviewer.common.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.ui.list.ListFragmentDirections

internal class RouterImpl(private val activity: AppCompatActivity) :
    Router {
    override fun goToDetails(id: Long) {
        val action = ListFragmentDirections.goToDetails(id)
        activity.findNavController(R.id.nav_host_fragment).navigate(action)
    }
}
