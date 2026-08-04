package com.abdullojon.maxwayclone.domain.repository

import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.UserData
import com.abdullojon.maxwayclone.data.source.remote.dto.response.branches.BranchData
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.order.OrderData
import com.abdullojon.maxwayclone.domain.model.ProductUIData
import com.abdullojon.maxwayclone.domain.model.ProductsByCategoryUIData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AppRepository {
    val cartFlow: StateFlow<Map<Int, Int>>
    val newOrderFlow: SharedFlow<OrderData>
    val userDataFlow: StateFlow<UserData?>
    fun getAllCategories(): Flow<Result<List<AllCategories>>>
    fun getProducts(): Flow<Result<List<ProductUIData>>>
    fun getProductsByCategory(): Flow<Result<List<ProductsByCategoryUIData>>>
    fun getAds(): Flow<Result<List<Ads>>>
    fun getStories(): Flow<Result<List<Stories>>>
    fun updateCount(productId: Int,count: Int)
    fun getBasketProducts(): Flow<List<ProductUIData>>
    fun clearCart()
    fun searchProducts(query: String): Flow<Result<List<ProductUIData>>>
    fun isUserLoggedIn(): Boolean
    suspend fun register(phone: String): Result<Unit>
    suspend fun verify(phone: String,code: Int): Result<String>
    suspend fun repeat(phone: String): Result<Unit>
    suspend fun createOrder(latitude: String, longitude: String, address: String): Result<OrderData>
    fun logout()
    fun getMyOrders(): Flow<Result<List<OrderData>>>
    fun getBranches(): Flow<Result<List<BranchData>>>
    suspend fun getUserInfo(): Result<UserData>
    suspend fun updateUserInfo(name: String, birthDate: String): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
}
