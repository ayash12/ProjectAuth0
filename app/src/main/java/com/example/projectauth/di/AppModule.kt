package com.example.projectauth.di

import android.app.Application
import android.content.Context
import com.auth0.android.Auth0
import com.example.projectauth.R
import com.example.projectauth.data.repository.AuthRepositoryImpl
import com.example.projectauth.domain.repository.AuthRepository
import com.example.projectauth.utils.SessionManager
import com.example.projectauth.utils.SessionManagerInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuth0(application: Application): Auth0 {
        val clientId = application.getString(R.string.com_auth0_client_id)
        val domain = application.getString(R.string.com_auth0_domain)
        return Auth0(clientId, domain)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth0: Auth0,
        context: Context,
        sessionManager: SessionManagerInterface
    ): AuthRepository {
        return AuthRepositoryImpl(auth0, context, sessionManager) // Menyediakan implementasi AuthRepository
    }

    @Provides
    @Singleton
    fun provideSessionManager(application: Application): SessionManagerInterface {
        return SessionManager(application.applicationContext) // Menggunakan application context untuk SessionManager
    }
}

