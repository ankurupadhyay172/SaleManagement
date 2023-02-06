package com.ankurupadhyay.salemanagement.data

import java.util.*

data class UserModel(val id:Int,val user_id:String,val user_name:String,val shop_name:String,
                    val shop_place:String,val user_status:Int,val pass:String,val timestamp: String)