package com.c242ps187.kidzlearnapp.data.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("error")
	val error: String
)
