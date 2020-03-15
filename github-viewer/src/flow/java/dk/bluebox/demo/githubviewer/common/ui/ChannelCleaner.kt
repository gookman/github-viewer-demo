package dk.bluebox.demo.githubviewer.common.ui

import dk.bluebox.demo.githubviewer.common.ui.utils.Cleaner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel

@ExperimentalCoroutinesApi
class ChannelCleaner : Cleaner<BroadcastChannel<*>> {

    private val channels = mutableListOf<BroadcastChannel<*>>()

    override fun add(item: BroadcastChannel<*>) {
        channels.add(item)
    }

    override fun clean() {
        channels.forEach { it.cancel() }
    }
}