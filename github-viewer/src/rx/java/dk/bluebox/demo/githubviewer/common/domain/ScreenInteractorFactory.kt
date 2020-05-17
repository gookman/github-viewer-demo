package dk.bluebox.demo.githubviewer.common.domain

import kotlin.reflect.KClass

interface ScreenInteractorFactory {
    fun <T : ScreenInteractor> createInteractor(clazz: KClass<T>): T
}