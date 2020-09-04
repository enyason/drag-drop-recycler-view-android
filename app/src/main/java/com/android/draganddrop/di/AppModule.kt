package com.android.draganddrop.di

import android.content.Context
import androidx.room.Room
import com.android.draganddrop.App
import com.android.draganddrop.BuildConfig
import com.android.draganddrop.data.repositories.TodoRepositoryImpl
import com.android.draganddrop.data.sources.LocalDataSource
import com.android.draganddrop.data.sources.RemoteDataSource
import com.android.draganddrop.domain.repositories.TodoRepository
import com.android.draganddrop.local.AppDataBase
import com.android.draganddrop.local.LocalDataSourceImpl
import com.android.draganddrop.remote.api.ToDoApi
import com.android.draganddrop.remote.source.RemoteDataSourceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {
    private val CONNECT_TIMEOUT = 10L
    private val READ_TIMEOUT = 10L
    private val WRITE_TIMEOUT = 10L

    @Provides
    @Singleton
    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()
    }

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): ToDoApi {
        return retrofit.create(ToDoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJiggleRemote(local: LocalDataSourceImpl): LocalDataSource = local

    @Provides
    @Singleton
    fun providePlanRemote(remote: RemoteDataSourceImpl): RemoteDataSource = remote

    @Provides
    @Singleton
    fun repository(todoRepository: TodoRepositoryImpl): TodoRepository = todoRepository

    @Provides
    @Singleton
    fun provideDb(app: App): AppDataBase {

        return Room.databaseBuilder(
            app.applicationContext,
            AppDataBase::class.java, "test_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}