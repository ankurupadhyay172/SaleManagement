package com.ankurupadhyay.salemanagement.worker

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ankurupadhyay.salemanagement.BuildConfig
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.request.SyncDataRequest
import com.ankurupadhyay.salemanagement.data.request.SyncOrderRequest
import com.ankurupadhyay.salemanagement.database.DatabaseManagerImpl
import com.ankurupadhyay.salemanagement.retrofit.HomeApiService
import com.ankurupadhyay.salemanagement.session.SessionManagerImpl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class SyncDataWorker (val context: Context,val workerParameters: WorkerParameters):Worker(context,workerParameters)
{

    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(context,"workmanager")
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Process is running").setContentText("this is text").build()
        setForegroundAsync(ForegroundInfo(11,notification))
        val data =inputData.getString("products")
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(HomeApiService::class.java)
        val type = TypeToken.getParameterized(MutableList::class.java, Products::class.java).type
        val list =Gson().fromJson<List<Products>>(data,type)
        val databaseManager = DatabaseManagerImpl(context)
        val sessionManager = SessionManagerImpl(context)
        for (i in list)
        {
            CoroutineScope(Dispatchers.IO).launch {
              val response =   service.syncData(SyncDataRequest(i.id,i.name,i.list))
                Log.d("mylogdata", "doWork: ${i.list}")
            }
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            val list2 = databaseManager.getAllOrders()
//            for (i in list2){
//                service.syncOrders(SyncOrderRequest(sessionManager.getUser()?.id.toString(),i))
//            }
//        }
//        databaseManager.updateUser(Calendar.getInstance().time)

        return Result.success(workDataOf(Pair("result","Sync Completed Successfully")))
    }
}