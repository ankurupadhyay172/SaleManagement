package com.ankurupadhyay.salemanagement.data.response


import android.icu.text.SimpleDateFormat
import android.util.Log
import com.ankurupadhyay.salemanagement.data.MyCart
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


data class ReceiveOrderSyncResponse(val status:Int, val result:List<ReceiveOrder>?)

data class ReceiveOrder(val id: String,
                        val order_id: Int,
                        val payment_type: Int,
                        val customer_name: String?,
                        val contact_no: String?="na",
                        val discount: Double=0.0,
                        val order_date: Long,
                        val user_id:String?,
                        val last_sync:String?,
                        val items: List<OrderVariant>?){




}

data class OrderVariant(val id:String,val order_id:String,val name:String,val description:String,
                        val ac_price:Double,val selling_price:Double,val quantity:Int,val pid:String,
                        val vid:Int,
                        val p_name:String,val discount: Double)