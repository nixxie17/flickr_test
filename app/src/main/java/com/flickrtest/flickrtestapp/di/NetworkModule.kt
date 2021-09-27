package com.flickrtest.flickrtestapp.di

import com.flickrtest.dataprovider.FlickrApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

//private Api key just for this showcase
private const val API_KEY = "96358825614a5d3b1a1c3fd87fca2b47"
private const val BASE_URL = "https://api.flickr.com/services/rest/"
private const val API_RESPONSE_FORMAT = "json"
private const val NO_JSON_CALLBACK = "1"
//99ecf90de337e31b6e4c7830b4f5e118

val networkModule = module {

    single { provideFlickrService(retrofit = get()) }

    single { provideRetrofit(okHttpClient = get(), url = BASE_URL) }

    single { provideOkHttpClient() }
}

internal fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("format", API_RESPONSE_FORMAT)
                    .addQueryParameter("nojsoncallback", NO_JSON_CALLBACK)
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        .build()
}

internal fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

internal fun provideFlickrService(retrofit: Retrofit): FlickrApi =
    retrofit.create(FlickrApi::class.java)