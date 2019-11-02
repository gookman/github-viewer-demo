package dk.bluebox.demo.githubviewer.flow.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory.Companion.BASE_URL
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory.Companion.TIMEOUT_SECCONDS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ServiceFactoryImpl : ServiceFactory {

    override fun <T> createService(serviceClass: Class<T>, interceptors: List<Interceptor>, jsonAdapters: List<Any>): T {
        val okHttpClient = createOKHttpClient(interceptors)

        val moshi = createMoshi(jsonAdapters)

        val retrofit = createRetrofit(okHttpClient, moshi)

        return retrofit.create(serviceClass)
    }

    private fun createOKHttpClient(interceptors: List<Interceptor>): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECCONDS, TimeUnit.SECONDS)

        interceptors.forEach { builder.addInterceptor(it) }

        return builder.build()
    }

    private fun createMoshi(adapters: List<Any>): Moshi {
        val builder = Moshi.Builder()

        adapters.forEach { builder.add(it) }

        builder.add(KotlinJsonAdapterFactory())

        return builder.build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }
}