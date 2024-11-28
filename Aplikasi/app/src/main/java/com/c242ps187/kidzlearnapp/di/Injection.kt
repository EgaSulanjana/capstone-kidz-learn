package com.c242ps187.kidzlearnapp.di

import android.content.Context
import com.c242ps187.kidzlearnapp.data.api.ApiConfig
import com.c242ps187.kidzlearnapp.data.pref.UserPreference
import com.c242ps187.kidzlearnapp.data.pref.dataStore
import com.c242ps187.kidzlearnapp.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(token)
        return UserRepository.getInstance(pref, apiService)
    }
}