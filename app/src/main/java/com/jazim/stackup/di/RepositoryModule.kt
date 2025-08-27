package com.jazim.pixelnews.di

import com.jazim.stackup.data.repository.UsersRepositoryImpl
import com.jazim.stackup.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository
}