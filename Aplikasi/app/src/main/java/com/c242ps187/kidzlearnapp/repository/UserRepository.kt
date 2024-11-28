package com.c242ps187.kidzlearnapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c242ps187.kidzlearnapp.data.api.ApiService
import com.c242ps187.kidzlearnapp.data.pref.UserPreference
import com.c242ps187.kidzlearnapp.data.response.Response
import com.c242ps187.kidzlearnapp.data.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    fun register(username: String, email: String, password: String): LiveData<Result<Response>> = liveData  {
        emit(Result.Loading)
        try{
            val response = apiService.register(username, email, password)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, Response::class.java)
            val errorMessage = errorBody.error
            emit(Result.Error(errorMessage))
        }
    }

    fun login(email: String, password: String): LiveData<Result<Response>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, Response::class.java)
            val errorMessage = errorBody.error
            emit(Result.Error(errorMessage))
        }
    }

    suspend fun saveSession(token: String) {
        userPreference.saveSession(token)
    }

    fun getSession(): Flow<String> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository = UserRepository(userPreference, apiService)
    }
}