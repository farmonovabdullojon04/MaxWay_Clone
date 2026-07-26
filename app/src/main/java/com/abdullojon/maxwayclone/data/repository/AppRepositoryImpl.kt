package com.abdullojon.maxwayclone.data.repository

import com.abdullojon.maxwayclone.data.remote.api.CategoriesApi
import com.abdullojon.maxwayclone.data.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: CategoriesApi
): AppRepository {
    override fun getAllCategories(): Flow<Result<List<AllCategories>>> = flow {
        val response=api.getAllCategory()

        if (response.isSuccessful && response.body()!=null){
            val categories=response.body()!!.data
            emit(Result.success(categories))
        }else{
            emit(Result.failure(Exception("Serverdan notugri javob keldi: ${response.code()}")))
        }
    }.catch { e->
        emit(Result.failure(e))
    }
}