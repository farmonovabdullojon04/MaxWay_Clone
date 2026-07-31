package com.abdullojon.maxwayclone.data.source.remote.dto.request

data class RegisterRequest(val phone: String)
data class VerifyRequest(val phone: String,val code: Int)
data class RepeatRequest(val phone: String)