package com.ankurupadhyay.salemanagement.repository

import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.User
import com.ankurupadhyay.salemanagement.data.request.*
import com.ankurupadhyay.salemanagement.data.response.CommonResponseModel
import com.ankurupadhyay.salemanagement.data.response.LoginResponse
import com.ankurupadhyay.salemanagement.data.response.SyncDataResponse
import com.ankurupadhyay.salemanagement.data.response.SyncReceiveDataResponse
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.retrofit.HomeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(val homeApiService: HomeApiService) {

    suspend fun syncData(syncDataRequest: SyncDataRequest): Flow<SyncDataResponse> = flow {
        val response = homeApiService.syncData(syncDataRequest)
        emit(response)
    }

    suspend fun syncReceiveData(): Flow<SyncReceiveDataResponse> = flow {
        val response = homeApiService.receiveSyncData()
        emit(response)
    }

    suspend fun syncProductVariant(syncProductVariantDataRequest: SyncProductVariantDataRequest): Flow<CommonResponseModel> = flow {
        val response = homeApiService.syncProductVariantData(syncProductVariantDataRequest)
        emit(response)
    }

    suspend fun syncReceiveData(commonRequestModel: CommonRequestModel): Flow<SyncReceiveDataResponse> = flow {
        val response = homeApiService.receiveSyncData(commonRequestModel)
        emit(response)
    }

    suspend fun loginUser(user: LoginRequest): Flow<LoginResponse> = flow {
        val response = homeApiService.loginUser(user)
        emit(response)
    }

    suspend fun syncOrder(syncOrderRequest: SyncOrderRequest): Flow<CommonResponseModel> = flow {
        val response = homeApiService.syncOrders(syncOrderRequest)
        emit(response)
    }

    suspend fun syncReplaceOrder(syncOrderRequest: SyncReplaceOrderRequest): Flow<CommonResponseModel> = flow {
        val response = homeApiService.syncReplaceOrders(syncOrderRequest)
        emit(response)
    }

    suspend fun deleteProduct(commonRequestModel: CommonRequestModel): Flow<CommonResponseModel> = flow {
        val response = homeApiService.deleteProduct(commonRequestModel)
        emit(response)
    }

    suspend fun deleteProductVariant(commonRequestModel: CommonRequestModel): Flow<CommonResponseModel> = flow {
        val response = homeApiService.deleteProductVariant(commonRequestModel)
        emit(response)
    }
}