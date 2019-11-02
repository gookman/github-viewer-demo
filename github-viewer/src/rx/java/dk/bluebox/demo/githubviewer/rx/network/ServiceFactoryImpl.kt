package dk.bluebox.demo.githubviewer.rx.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory.Companion.BASE_URL
import dk.bluebox.demo.githubviewer.common.network.ServiceFactory.Companion.TIMEOUT_SECCONDS
import dk.bluebox.demo.githubviewer.rx.SchedulersProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ServiceFactoryImpl(private val schedulersProvider: SchedulersProvider) : ServiceFactory {

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
        val rxCallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(schedulersProvider.io())

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(rxCallAdapterFactory)
                .build()
    }
}