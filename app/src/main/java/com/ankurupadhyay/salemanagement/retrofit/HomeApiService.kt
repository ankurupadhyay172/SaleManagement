package com.ankurupadhyay.salemanagement.retrofit

import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.data.request.*
import com.ankurupadhyay.salemanagement.data.response.*
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeApiService {


    @POST("addProductAndVariant.php")
    suspend fun syncData(@Body request: SyncDataRequest):SyncDataResponse

    @POST("UpdateProductVariants.php")
    suspend fun syncProductVariantData(@Body request: SyncProductVariantDataRequest):CommonResponseModel

    @POST("readSyncData.php")
    suspend fun receiveSyncData():SyncReceiveDataResponse

    @POST("SyncProducts.php")
    suspend fun receiveSyncData(@Body commonRequestModel: CommonRequestModel):SyncReceiveDataResponse

    @POST("loginUser.php")
    suspend fun loginUser(@Body request: LoginRequest):LoginResponse

    @POST("UpdateOrders.php")
    suspend fun syncOrders(@Body syncOrderRequest: SyncOrderRequest):CommonResponseModel

    @POST("UpdateReplaceOrders.php")
    suspend fun syncReplaceOrders(@Body syncOrderRequest: SyncReplaceOrderRequest):CommonResponseModel

    @POST("receiveSyncOrders.php")
    suspend fun receiveSyncOrders(@Body commonRequestModel: CommonRequestModel):ReceiveOrderSyncResponse

    @POST("ReceiveSyncReplaceOrders.php")
    suspend fun receiveSyncReplaceOrders(@Body commonRequestModel: CommonRequestModel):ReceiveOrderSyncResponse

    @POST("DeleteProductVariant.php")
    suspend fun deleteProductVariant(@Body commonRequestModel: CommonRequestModel):CommonResponseModel

    @POST("DeleteProduct.php")
    suspend fun deleteProduct(@Body commonRequestModel: CommonRequestModel):CommonResponseModel

}