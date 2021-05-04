package com.raddyr.movieviewer.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raddyr.movieviewer.BuildConfig
import com.raddyr.movieviewer.retrofit.TheMovieDbRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val API_KEY = "api_key"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter(API_KEY, BuildConfig.API_KEY).build()
                original = original.newBuilder().url(url).build()
                chain.proceed(original)
            }.addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): TheMovieDbRetrofit {
        return retrofit
            .build()
            .create(TheMovieDbRetrofit::class.java)
    }
}


