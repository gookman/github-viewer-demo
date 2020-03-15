package dk.bluebox.demo.githubviewer.common.rx

import dk.bluebox.demo.githubviewer.common.rx.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class AndroidSchedulersProvider :
    SchedulersProvider {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

    override fun io() = Schedulers.io()

    override fun newThread() = Schedulers.newThread()

    override fun trampoline() = Schedulers.trampoline()
}