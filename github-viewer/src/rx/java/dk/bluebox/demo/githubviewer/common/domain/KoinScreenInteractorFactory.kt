package dk.bluebox.demo.githubviewer.common.domain

import org.koin.core.KoinComponent
import kotlin.reflect.KClass

class KoinScreenInteractorFactory : ScreenInteractorFactory, KoinComponent {
    override fun <T : ScreenInteractor> createInteractor(clazz: KClass<T>): T {
        return getKoin().get(clazz, null, null)
    }
}