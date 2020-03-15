package dk.bluebox.demo.githubviewer.common.ui

import dk.bluebox.demo.githubviewer.common.ui.utils.Cleaner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableCleaner : Cleaner<Disposable> {

    private val disposables = CompositeDisposable()

    override fun clean() {
        disposables.clear()
    }

    override fun add(item: Disposable) {
        disposables.add(item)
    }
}