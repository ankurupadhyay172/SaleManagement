package com.ankurupadhyay.salemanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Products(
    @PrimaryKey
    var id: String,
    val name: String,
    val list:List<Variants>?=null,
    var selectedVariant:Variants?=null,
    var lastSync:Date?,
    var type:String,
    var isVisible:Boolean=true,
    val serverId:Int
){
    fun setSelectedDefault(item: Int=0): Variants? {
        return if (list.isNullOrEmpty())
            null
        else {
            selectedVariant = list[item]
            list[item]
        }
    }

    fun localToServerId(userId: Int?):Int{
        val serverId = id.toString()+userId
        return serverId.toInt()
    }
    fun serverToLocalId(userId: String,id: Int):Int{
        val localId = id.toString().replace(userId,"")
        return localId.toInt()
    }

}