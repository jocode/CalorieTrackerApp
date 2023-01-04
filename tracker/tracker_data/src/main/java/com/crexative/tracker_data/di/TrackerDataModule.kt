package com.crexative.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.crexative.tracker_data.local.TrackerDao
import com.crexative.tracker_data.local.TrackerDatabase
import com.crexative.tracker_data.remote.OpenFoodApi
import com.crexative.tracker_data.repository.TrackerRepositoryImpl
import com.crexative.tracker_domain.repository.TrackerRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(retrofit: Retrofit) : OpenFoodApi {
        return retrofit.create(OpenFoodApi::class.java)
    }

    /**
     * Local Database
     */
    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application) : TrackerDatabase {
        return Room.databaseBuilder(
            app,
            TrackerDatabase::class.java,
            "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDatabase
    ) : TrackerRepository {
        return TrackerRepositoryImpl(
            api = api,
            dao = db.trackerDao()
        )
    }

}