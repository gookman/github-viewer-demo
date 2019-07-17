package dk.bluebox.demo.githubviewer.network

import okhttp3.Interceptor

interface ServiceFactory {
    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val TIMEOUT_SECCONDS = 30L
    }

    fun <T> createService(serviceClass: Class<T>, interceptors: List<Interceptor>, jsonAdapters: List<Any>): T
}