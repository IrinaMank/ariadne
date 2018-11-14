package com.zapir.ariadne.model.remote

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceFactory {

    private const val URL_ENDPOINT = "https://simple-mobile-api.herokuapp.com"//ToDo

    val routerService: RouterService by lazy { retrofit.create(RouterService::class
            .java) }

    private val retrofit by lazy { createRetrofit() }
    private val okHttpClient by lazy { createOkHttpClient() }

    private val log: Logger by lazy {
        LoggerFactory.getLogger("Retrofit")
    }

    private fun createRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(URL_ENDPOINT)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    private fun createOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor { log.debug(it) }
                            .apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            })
                    .build()

}