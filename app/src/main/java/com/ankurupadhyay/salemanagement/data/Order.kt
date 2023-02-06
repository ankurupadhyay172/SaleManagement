package com.ankurupadhyay.salemanagement.data

import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val paymentType: Int,
    val customerName: String?,
    val contactNo: String?="na",
    val discount: Double=0.0,
    val timestamp: Long?,
    val items: List<MyCart>?,
    val type:String
)
{
    fun timestampToDate():String{
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp!!
        val date: String = DateFormat.format("dd-MM-yyyy hh:mm aa", cal).toString()
        return date
    }

}