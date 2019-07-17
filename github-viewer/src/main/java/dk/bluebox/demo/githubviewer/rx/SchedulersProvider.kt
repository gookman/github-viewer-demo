package dk.bluebox.demo.githubviewer.rx

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun main(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun newThread(): Scheduler
    fun trampoline(): Scheduler
}