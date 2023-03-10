package com.ankurupadhyay.salemanagement.session

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModuleBinds {
    @Singleton
    @Binds
    abstract fun provideSessionManager(impl: SessionManagerImpl):SessionManager
}