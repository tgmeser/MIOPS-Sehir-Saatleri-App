package com.babyapps.citytimeapp.di

import android.content.Context
import androidx.room.Room
import com.babyapps.citytimeapp.data.db.CityTimeDao
import com.babyapps.citytimeapp.data.db.CityTimeDatabase
import com.babyapps.citytimeapp.data.remote.CityTimeApi
import com.babyapps.citytimeapp.data.repository.CityRepository
import com.babyapps.citytimeapp.util.Constants.BASE_URL
import com.babyapps.citytimeapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()


        @Provides
        @Singleton
        fun provideCityTimeApi(retrofit: Retrofit): CityTimeApi =
            retrofit.create(CityTimeApi::class.java
            )


     */
    @Provides
    @Singleton
    fun provideApi(): CityTimeApi =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BASE_URL
        ).build().create(CityTimeApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): CityTimeDatabase =
        Room.databaseBuilder(app, CityTimeDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDao(database: CityTimeDatabase): CityTimeDao = database.dao()

    @Provides
    @Singleton
    fun provideRepo(api: CityTimeApi,dao: CityTimeDao): CityRepository = CityRepository(api,dao)

}