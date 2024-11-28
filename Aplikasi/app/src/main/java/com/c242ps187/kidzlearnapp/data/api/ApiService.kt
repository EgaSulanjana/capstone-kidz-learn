package com.c242ps187.kidzlearnapp.data.api

import com.c242ps187.kidzlearnapp.data.response.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response

//    @FormUrlEncoded
//    @POST("questions")
//    suspend fun questions(
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Response
}