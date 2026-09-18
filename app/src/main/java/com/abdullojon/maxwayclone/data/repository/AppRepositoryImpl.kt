package com.abdullojon.maxwayclone.data.repository

import android.content.Context
import com.abdullojon.maxwayclone.data.mapper.toUIData
import com.abdullojon.maxwayclone.data.source.local.preference.Prefs
import com.abdullojon.maxwayclone.data.source.remote.api.auth_api.AuthApi
import com.abdullojon.maxwayclone.data.source.remote.api.branch_api.BranchesApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.*
import com.abdullojon.maxwayclone.data.source.remote.api.order_api.OrderApi
import com.abdullojon.maxwayclone.data.source.remote.dto.request.OrderProductItem
import com.abdullojon.maxwayclone.data.source.remote.dto.request.OrderRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.RegisterRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.RepeatRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.UpdateUserRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.VerifyRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.RegisterResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.UserData
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.order.OrderData
import com.abdullojon.maxwayclone.domain.model.ProductUIData
import com.abdullojon.maxwayclone.domain.model.ProductsByCategoryUIData
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import com.abdullojon.maxwayclone.util.NotificationHelper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiCategory: CategoriesApi,
    private val apiProduct: ProductsApi,
    private val adsApi: AdsApi,
    private val storiesApi: StoriesApi,
    private val authApi: AuthApi,
    private val orderApi: OrderApi,
    private val branchesApi: BranchesApi,
    private val prefs: Prefs,
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context
): AppRepository {
    private val _cartFlow = MutableStateFlow<Map<Int, Int>>(emptyMap())
    override val cartFlow: StateFlow<Map<Int, Int>> = _cartFlow.asStateFlow()

    private val _newOrderFlow = MutableSharedFlow<OrderData>(replay = 1)
    override val newOrderFlow = _newOrderFlow.asSharedFlow()

    private val _userDataFlow = MutableStateFlow<UserData?>(null)
    override val userDataFlow: StateFlow<UserData?> = _userDataFlow.asStateFlow()

    init {
       // logout()
    }
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

    override suspend fun register(phone: String): Result<Unit> {
        return try {
            val response = authApi.register(RegisterRequest(phone))
            if (response.isSuccessful && response.body() != null) {
                val registerData: RegisterResponse = response.body()!!.data
                NotificationHelper.showSmsNotification(context, registerData.code.toString())
                Result.success(Unit)
            } else {
                Result.failure(Exception("Xatolik: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verify(
        phone: String,
        code: Int
    ): Result<String> {
        return try {
            val response = authApi.verify(VerifyRequest(phone, code))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.data.token
                prefs.token = token
                prefs.isLoggedIn = true
                Result.success(token)
            } else {
                Result.failure(Exception("Tasdiqlashda xatolik"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun repeat(phone: String): Result<Unit> {
        return try {
            val response = authApi.repeat(RepeatRequest(phone))
            if (response.isSuccessful && response.body() != null) {
                val registerData: RegisterResponse = response.body()!!.data
                NotificationHelper.showSmsNotification(context, registerData.code.toString())
                Result.success(Unit)
            } else {
                Result.failure(Exception("Qayta yuborishda xatolik"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createOrder(
        latitude: String,
        longitude: String,
        address: String
    ): Result<OrderData> {
        return try {
            val cartItems = cartFlow.value.map { (id, count) ->
                OrderProductItem(productID = id, count = count)
            }
            val request = OrderRequest(
                ls = cartItems,
                latitude = latitude,
                longitude = longitude,
                address = address
            )
            val response = orderApi.createOrder(token = prefs.token ?: "", request = request)
            if (response.isSuccessful && response.body() != null) {
                val newOrder = response.body()!!.data
                _newOrderFlow.emit(newOrder)
                Result.success(newOrder)
            } else {
                Result.failure(Exception("Buyurtma yaratishda xatolik"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        prefs.clearUser()
        _cartFlow.value = emptyMap()
        _userDataFlow.value = null
    }

    override fun getMyOrders(): Flow<Result<List<OrderData>>> =flow{
        val response = orderApi.getMyOrders(token = prefs.token ?: "")
        if (response.isSuccessful && response.body() != null) {
            emit(Result.success(response.body()!!.data))
        } else {
            emit(Result.failure(Exception("Xatolik")))
        }
    }.catch { emit(Result.failure(it)) }

    override suspend fun getUserInfo(): Result<UserData> {
        return try {
            val response = authApi.getUserInfo(token = prefs.token ?: "")
            if (response.isSuccessful && response.body() != null) {
                val userData = response.body()!!.data
                _userDataFlow.value = userData
                Result.success(userData)
            } else {
                Result.failure(Exception("Foydalanuvchi ma'lumotlarini olishda xatolik"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserInfo(name: String, birthDate: String): Result<Unit> {
        return try {
            val response = authApi.updateUserInfo(
                token = prefs.token ?: "",
                body = UpdateUserRequest(name, birthDate)
            )
            if (response.isSuccessful) {
                // Update local flow with new data
                val current = _userDataFlow.value
                _userDataFlow.value = current?.copy(name = name, birthDate = birthDate)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ma'lumotlarni yangilashda xatolik"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            val response = authApi.deleteAccount(token = prefs.token ?: "")
            if (response.isSuccessful) {
                logout()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Accountni o'chirishda xatolik"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getBranches(): Flow<Result<List<com.abdullojon.maxwayclone.data.source.remote.dto.response.branches.BranchData>>> = flow {
        val response = branchesApi.getBranches()
        if (response.isSuccessful && response.body() != null) {
            emit(Result.success(response.body()!!.data))
        } else {
            emit(Result.failure(Exception("Filiallarni yuklashda xatolik")))
        }
    }.catch { emit(Result.failure(it)) }
}
