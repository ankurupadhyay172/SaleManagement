package com.ankurupadhyay.salemanagement.ui

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ankurupadhyay.salemanagement.base.BaseViewModel
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.data.request.*
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.repository.HomeRepository
import com.ankurupadhyay.salemanagement.session.SessionManager
import com.ankurupadhyay.salemanagement.utils.AnalyticsType
import com.ankurupadhyay.salemanagement.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val databaseManager: DatabaseManager,
                                        private val repository: HomeRepository,
                                        val sessionManager: SessionManager ):BaseViewModel(){


    var analyticsType = MutableLiveData(Calendar.getInstance().time)
    val instance: Calendar = Calendar.getInstance()
    var filterValue = MutableLiveData(AnalyticsType.DayByDay.type)
    var discount = 0.0
    var cartTotal = 0.0
    var grandTotal = 0.0


    suspend fun getUserId():String{

            return databaseManager.getUsers()?.id.toString()

    }

    fun getSyncData(syncDataRequest: SyncDataRequest) = liveData(Dispatchers.IO){
        repository.syncData(syncDataRequest).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun syncReceiveData(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.syncReceiveData(commonRequestModel).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun deleteProductVariant(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.deleteProductVariant(commonRequestModel).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun deleteProduct(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.deleteProduct(commonRequestModel).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun getAllOrders() = liveData(Dispatchers.IO){
        emit(databaseManager.getAllOrders())
    }

    fun getAllReplaceOrders() = liveData(Dispatchers.IO){
        emit(databaseManager.getAllReplaceOrders())
    }
    fun getUserFlow() = liveData(Dispatchers.IO){
        emit(databaseManager.getUsers())
    }


    fun syncProductVariant() = liveData(Dispatchers.IO){
        val userId = databaseManager.getUsers()?.id
        val list = databaseManager.getQueryTypeProduct()
        val newSyncModel = SyncProductVariantDataRequest(userId,list)

        repository.syncProductVariant(newSyncModel).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun syncOrderData(syncOrderRequest: SyncOrderRequest) = liveData(Dispatchers.IO) {
        repository.syncOrder(syncOrderRequest).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun syncReplaceOrderData(syncOrderRequest: SyncReplaceOrderRequest) = liveData(Dispatchers.IO) {
        repository.syncReplaceOrder(syncOrderRequest).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun loginUser(user:LoginRequest) = liveData(Dispatchers.IO) {
        repository.loginUser(user).toLoadingState().catch { e->
            Log.d("myLogError", "getSyncData: ${e.message}")
        }.collect{
            emit(it)
        }
    }

    fun getUser() = liveData(Dispatchers.IO) {
        emit(databaseManager.getUsers())
    }





    fun getSingleProduct(id:String) = liveData(Dispatchers.IO) {
        emit(databaseManager.getSingleProduct(id))
    }

    fun deleteProduct(id:String){
        databaseManager.deleteSingleProduct(id)
    }

    fun getAllProducts() = liveData(Dispatchers.IO){
        emit(databaseManager.getProducts())
    }

    fun getProductsFlow() = liveData(Dispatchers.IO){
        databaseManager.getProductsFlow()?.collect{
            emit(it)
        }

    }

    fun getCartFlow()  = liveData(Dispatchers.IO){
        databaseManager.getCartFlow().collect{
            emit(it)
        }
    }

    fun getProductVariant(id: String)= liveData {
        databaseManager.getProductVariants(id).collect{
            emit(it)
        }
    }

    fun deleteSingleVariant(id:String)
    {
        databaseManager.deleteSingleVariant(id)
    }

    fun addToCart(id: String, variants: Variants, qua: Int) {
        databaseManager.addToCart(MyCart(id,variants,qua))
    }

    fun updateCartQua(id:String,qua: Int)
    {
        CoroutineScope(Dispatchers.IO).launch {
            databaseManager.updateCartQun(id,qua)
        }

    }

    fun updateVariant(model:Variants)
    {
        databaseManager.updateVariant(model)
    }

    fun getProductVariantFlow(id:String) = liveData(Dispatchers.IO) {
        databaseManager.getProductVariants(id).collect{
            emit(it)
        }

    }

    fun updateProductVariantList(id:String,list: List<Variants>){
        databaseManager.updateProductVariant(id,list)
    }

    fun deleteSingleCart(id: String) {
        databaseManager.deleteSingleCart(id)
    }

    fun getDateWiseFlow(date: Date) = liveData(Dispatchers.IO){
        databaseManager.getOrderByDate(date).collect{
            emit(it)
        }
    }

    fun getMonthWiseOrder(date: Date) = liveData(Dispatchers.IO){
        databaseManager.getOrderByMonth(date).collect{
            emit(it)
        }
    }

    fun getYearWiseOrder(date: Date) = liveData(Dispatchers.IO){
        databaseManager.getOrderByYear(date).collect{
            emit(it)
        }
    }

    fun incrementCounter() {
        filterValue.value?.let {
            when(it)
            {
                AnalyticsType.DayByDay.type->instance.add(Calendar.DATE, 1)
                AnalyticsType.MonthByMonth.type->instance.add(Calendar.MONTH, 1)
                AnalyticsType.YearByYear.type->instance.add(Calendar.YEAR, 1)
            }
        }

        analyticsType.value = instance.time

    }



    fun decrementCounter() {
        filterValue.value?.let {
            when(it)
            {
                AnalyticsType.DayByDay.type->instance.add(Calendar.DATE, -1)
                AnalyticsType.MonthByMonth.type->instance.add(Calendar.MONTH, -1)
                AnalyticsType.YearByYear.type->instance.add(Calendar.YEAR, -1)
            }
        }
        analyticsType.value = instance.time
    }

    fun setFilterData(date:Date):String{
        return when(filterValue.value) {
            AnalyticsType.DayByDay.type-> SimpleDateFormat("dd-MMM-yyyy").format(date)
            AnalyticsType.MonthByMonth.type-> SimpleDateFormat("MMM-YYYY").format(date)
            AnalyticsType.YearByYear.type-> SimpleDateFormat("YYYY").format(date)
            else-> SimpleDateFormat("dd-MMM-yyyy").format(date)
        }
    }

    fun getSingleVariant(id:String) = liveData {
        emit(databaseManager.getSingleVariant(id))
    }
}