package com.abdullojon.maxwayclone.presentation.register

data class RegisterState(
    val phone: String="",
    val code: String="",
    val name: String="",
    val isLoading: Boolean=false,
    val error: String?=null
)
