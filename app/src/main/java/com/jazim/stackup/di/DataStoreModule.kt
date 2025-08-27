package com.jazim.stackup.di

import android.content.Context
import com.jazim.stackup.data.datastore.FollowingDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideFollowedUsersDataStore(
        @ApplicationContext context: Context
    ): FollowingDataStore = FollowingDataStore(context)
}