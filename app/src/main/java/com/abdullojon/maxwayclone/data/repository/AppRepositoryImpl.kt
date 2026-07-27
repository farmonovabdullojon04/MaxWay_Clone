package com.abdullojon.maxwayclone.data.repository

import com.abdullojon.maxwayclone.data.remote.api.CategoriesApi
import com.abdullojon.maxwayclone.data.remote.api.ProductsApi
import com.abdullojon.maxwayclone.data.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.data.remote.dto.response.products.Product
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.security.PrivateKey
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiCategory: CategoriesApi,
    private val apiProduct: ProductsApi
): AppRepository {
    override fun getAllCategories(): Flow<Result<List<AllCategories>>> = flow {
        val response=apiCategory.getAllCategory()

        if (response.isSuccessful && response.body()!=null){
            val categories=response.body()!!.data
            emit(Result.success(categories))
        }else{
            emit(Result.failure(Exception("Serverdan notugri javob keldi: ${response.code()}")))
        }
    }.catch { e->
        emit(Result.failure(e))
    }

    override fun getProducts(): Flow<Result<List<Product>>> =flow{
        val response=apiProduct.getProducts()
        if (response.isSuccessful && response.body()!=null){
            val products=response.body()!!.data
            emit(Result.success(products))
        }else{
            emit(Result.failure(Exception("Serverdan notugri javob keldi: ${response.code()}")))
        }
    }.catch { e->
        emit(Result.failure(e))
    }

}