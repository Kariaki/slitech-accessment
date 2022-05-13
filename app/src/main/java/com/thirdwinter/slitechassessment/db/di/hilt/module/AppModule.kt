package com.thirdwinter.slitechassessment.db.di.hilt.module

import android.content.Context
import androidx.room.Room
import com.thirdwinter.slitechassessment.db.architecture.dao.FootballDao
import com.thirdwinter.slitechassessment.db.architecture.dao.api.FootballApi
import com.thirdwinter.slitechassessment.db.architecture.dao.api.fakeApi.FakeFootballApiDatasource
import com.thirdwinter.slitechassessment.db.architecture.database.FootballDatabase
import com.thirdwinter.slitechassessment.db.architecture.repository.Repository
import com.thirdwinter.slitechassessment.db.architecture.repository.fake.FakeFootballRepository
import com.thirdwinter.slitechassessment.db.architecture.repository.FootballRepository
import com.thirdwinter.slitechassessment.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun retrofitInstance(): FootballApi = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FootballApi::class.java)

    @Singleton
    @Provides
    fun provideLocalDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FootballDatabase::class.java,
        "database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideYourDao(db: FootballDatabase) = db.footballDao()

    @Singleton
    @Named("datasource")
    @Provides
    fun provideDatasource(api: FootballApi, dao: FootballDao): Repository =
        FootballRepository(api, dao)

    @Singleton
    @Provides
    @Named("fakeDatasource")
    fun provideFakeDatasource(): Repository =
        FakeFootballRepository(FakeFootballApiDatasource())

}


