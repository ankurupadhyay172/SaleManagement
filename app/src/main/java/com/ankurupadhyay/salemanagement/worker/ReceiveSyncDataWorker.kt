package com.ankurupadhyay.salemanagement.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ankurupadhyay.salemanagement.BuildConfig
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.data.*
import com.ankurupadhyay.salemanagement.data.request.CommonRequestModel
import com.ankurupadhyay.salemanagement.database.DatabaseManagerImpl
import com.ankurupadhyay.salemanagement.repository.HomeRepository
import com.ankurupadhyay.salemanagement.retrofit.HomeApiService
import com.ankurupadhyay.salemanagement.session.SessionManagerImpl
import com.ankurupadhyay.salemanagement.utils.QueryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ReceiveSyncDataWorker(val context:Context,val workerParameters: WorkerParameters):Worker(context,workerParameters) {
    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(context,"workmanager")
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Process is running").setContentText("this is text").build()
        setForegroundAsync(ForegroundInfo(11,notification))
        //create logger
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        //create okhttp client
        val okhttp  = OkHttpClient.Builder().addInterceptor(logger).readTimeout(100,TimeUnit.SECONDS).connectTimeout(100,TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).client(okhttp.build()).build()
        val service = retrofit.create(HomeApiService::class.java)
        val databaseManagerImpl = DatabaseManagerImpl(context)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.receiveSyncData(CommonRequestModel(databaseManagerImpl.getUsers()?.id.toString()))
            response.result?.let {
                if (it.isNotEmpty()){
                    for (i in it) {
                        databaseManagerImpl.addProduct(Products(id=i.id,i.name,i.list,null,Calendar.getInstance().time,QueryType.NONE.type, serverId = i.serverId))
                        i.list?.let {
                            for (j in it){
                                databaseManagerImpl.addVariant(Variants(j.id,j.name,j.description,j.acPrice,j.sellingPrice,j.quantity,
                                    j.pid,j.pName,0,QueryType.NONE.type))
                            }
                        }
                    }
                }
            }
            val orders = service.receiveSyncOrders(CommonRequestModel(databaseManagerImpl.getUsers()?.id.toString()))
            orders.result?.let {
                if(it.isNotEmpty()){
                    for (i in it){
                        val list = mutableListOf<MyCart>()
                        i.items?.let {
                            for (j in it){
                                list.add(MyCart(j.id, Variants(j.id,j.name,j.description,j.ac_price,j.selling_price,j.quantity,
                                    j.pid,j.p_name,j.quantity,QueryType.NONE.type)))
                            }
                            databaseManagerImpl.addOrder(Order(i.order_id,i.payment_type,i.customer_name,i.contact_no,i.discount,i.order_date,list,QueryType.NONE.type))
                        }
                    }
                }
            }

            val replaceOrders = service.receiveSyncReplaceOrders(CommonRequestModel(databaseManagerImpl.getUsers()?.id.toString()))
            replaceOrders.result?.let {
                if(it.isNotEmpty()){
                    for (i in it){
                        val list = mutableListOf<ReplaceCart>()
                        i.items?.let {
                            for (j in it){
                                list.add(ReplaceCart(j.id, Variants(j.id,j.name,j.description,j.ac_price,j.selling_price,j.quantity,
                                    j.pid,j.p_name,j.quantity,QueryType.NONE.type)))
                            }
                            databaseManagerImpl.addReplaceOrder(ReplaceOrder(i.order_id,i.payment_type,i.customer_name,i.contact_no,i.discount,i.order_date,list,QueryType.NONE.type))
                        }
                    }
                }
            }
        }


        return Result.success()
    }
}