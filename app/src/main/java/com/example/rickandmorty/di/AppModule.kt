package com.example.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.data.repo.FavoriteDaoRepository
import com.example.rickandmorty.network.RetroInstance
import com.example.rickandmorty.network.RetroService
import com.example.rickandmorty.room.FavoriteDAO
import com.example.rickandmorty.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFavoriteDaoRepository(fdao: FavoriteDAO): FavoriteDaoRepository {
        return FavoriteDaoRepository(fdao)
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(@ApplicationContext context: Context): FavoriteDAO {
        val db = Room.databaseBuilder(context, RoomDatabase::class.java, "rickandmorty1.sqlite")
            .createFromAsset("rickandmorty.sqlite").build()
        return db.getFavorite()
    }

    @Provides
    @Singleton
    fun provideRetroService(@ApplicationContext context: Context): RetroService {
        return RetroInstance.getRetroInstance().create(RetroService::class.java)
    }

}