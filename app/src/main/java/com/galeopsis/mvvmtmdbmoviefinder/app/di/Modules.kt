package com.galeopsis.mvvmtmdbmoviefinder.app.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.galeopsis.mvvmtmdbmoviefinder.model.AppDatabase
import com.galeopsis.mvvmtmdbmoviefinder.model.api.MoviesApi
import com.galeopsis.mvvmtmdbmoviefinder.model.dao.MoviesDao
import com.galeopsis.mvvmtmdbmoviefinder.model.repository.MoviesRepository
import com.galeopsis.mvvmtmdbmoviefinder.viewmodel.MainViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"

val viewModelModule = module {
    single { MainViewModel(get()) }
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    single { provideUserApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }


    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "movies.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: AppDatabase): MoviesDao {
        return database.moviesDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(api: MoviesApi, dao: MoviesDao): MoviesRepository {
        return MoviesRepository(api, dao)
    }

    single { provideUserRepository(get(), get()) }
}