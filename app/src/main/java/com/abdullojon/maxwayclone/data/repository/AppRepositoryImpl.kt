package com.abdullojon.maxwayclone.data.repository

import com.abdullojon.maxwayclone.data.mapper.toUIData
import com.abdullojon.maxwayclone.data.source.local.preference.Prefs
import com.abdullojon.maxwayclone.data.source.remote.api.auth_api.AuthApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.AdsApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.CategoriesApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.ProductsApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.StoriesApi
import com.abdullojon.maxwayclone.data.source.remote.dto.request.RegisterRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.RepeatRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.VerifyRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.domain.model.ProductUIData
import com.abdullojon.maxwayclone.domain.model.ProductsByCategoryUIData
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiCategory: CategoriesApi,
    private val apiProduct: ProductsApi,
    private val adsApi: AdsApi,
    private val storiesApi: StoriesApi,
    private val authApi: AuthApi,
    private val prefs: Prefs
): AppRepository {
    private val _cartFlow = MutableStateFlow<Map<Int, Int>>(emptyMap())
    override val cartFlow: StateFlow<Map<Int, Int>> = _cartFlow.asStateFlow()

    private val products = ArrayList<ProductUIData>()
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

    override fun getProducts(): Flow<Result<List<ProductUIData>>> =flow{
        val response=apiProduct.getProducts()

         if(response.isSuccessful && response.body() != null) {
            val ls = response.body()!!.data.map {
                it.toUIData(cartFlow.value.getOrDefault(it.id, 0))
            }
            Result.success(ls)
        } else Result.failure(Exception("Producs olishda xatolik"))
    }

    override fun getProductsByCategory(): Flow<Result<List<ProductsByCategoryUIData>>> =flow {
        val response=apiProduct.getProductsByCategory()
        if (response.isSuccessful && response.body()!=null){

            val allApiProducts=response.body()!!.data.flatMap { it.products }

           val uiData=response.body()!!.data.map{apiCategory->
               ProductsByCategoryUIData(
                   id = apiCategory.id,
                   name = apiCategory.name,
                   products = apiCategory.products.map { it.toUIData(cartFlow.value.getOrDefault(it.id, 0)) }
               )
           }
            products.clear()
            products.addAll(allApiProducts.map { it.toUIData() })
            emit(Result.success(uiData))
        }
    }

    override fun getAds(): Flow<Result<List<Ads>>> = flow {
        val response=adsApi.getAds()
        if (response.isSuccessful && response.body()!=null){
            emit(Result.success(response.body()!!.data))
        }else{
            emit(Result.failure(Exception("Ads yuklashda xatolik")))
        }
    }.catch { emit(Result.failure(it)) }


    override fun getStories(): Flow<Result<List<Stories>>> =flow{
        val response=storiesApi.getStories()
        if (response.isSuccessful && response.body()!=null){
            emit(Result.success(response.body()!!.data))
        }else{
            emit(Result.failure(Exception("Storiesni yuklashda xatolik")))
        }
    }.catch { emit(Result.failure(it))}

    override fun updateCount(
        productId: Int,
        count: Int
    ) {
        val currentMap = cartFlow.value.toMutableMap()
        if (count <= 0) currentMap.remove(productId)
        else currentMap[productId] = count
        _cartFlow.value = currentMap
    }

    override fun getBasketProducts(): Flow<List<ProductUIData>> = cartFlow.map {currentCart->
        products.filter{currentCart.containsKey(it.id)}
            .map { it.copy(count = currentCart[it.id]?:0) }
    }

    override fun clearCart() {
        _cartFlow.value=emptyMap()
    }

    override fun searchProducts(
        query: String
    ): Flow<Result<List<ProductUIData>>> =flow{
        val response=apiProduct.searchProducts(query)
        if (response.isSuccessful && response.body()!=null){
            val data=response.body()!!.data.map {
                it.toUIData(cartFlow.value.getOrDefault(it.id,0))
            }
            emit(Result.success(data))
        }else{
            emit(Result.failure(Exception("Qidiruvda xatolik")))
        }
    }.catch { emit(Result.failure(it)) }

    override fun isUserLoggedIn(): Boolean=prefs.isLoggedIn

    override suspend fun register(
        phone: String
    ): Result<Unit> {
        val response=authApi.register(RegisterRequest(phone))
        return if (response.isSuccessful) Result.success(Unit)
        else Result.failure(Exception("Xatolik: ${response.code()}"))
    }

    override suspend fun verify(
        phone: String,
        code: Int
    ): Result<String> {
        val response=authApi.verify(VerifyRequest(phone,code))
        if (response.isSuccessful && response.body()!=null){
            val token = response.body()!!.data.token
            prefs.token = token
            prefs.isLoggedIn = true
            return Result.success(token)
        }
        return Result.failure(Exception("Tasdiqlashda xatolik"))
    }

    override suspend fun repeat(
        phone: String
    ): Result<Unit> {
       val response=authApi.repeat(RepeatRequest(phone))
        return if (response.isSuccessful) Result.success(Unit)
        else Result.failure(Exception("Qayta yuborishda xatolik"))
    }

}