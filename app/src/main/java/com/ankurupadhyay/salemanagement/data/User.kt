package com.ankurupadhyay.salemanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id:Int,val user_id:String,val user_name:String,val shop_name:String,
    val shop_place:String,val user_status:Int,val pass:String,val timestamp: Date
)