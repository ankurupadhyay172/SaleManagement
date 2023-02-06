package com.ankurupadhyay.salemanagement.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*
import javax.annotation.Nullable

@Entity
data class Variants(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val acPrice: Double,
    val sellingPrice: Double,
    var quantity: Int,
    val pid:String,
    val pName:String?,
    var selectedQuan:Int,
    var type:String?,
    var isVisible:Boolean=true,

    var vid:Int = 0){

    fun localToServerId(userId:String):Int{
        val serverId = id.toString()+userId
        return serverId.toInt()
    }

    fun serverToLocalId(userId: String,id: Int):Int{
        val localId = id.toString().replace(userId,"")
        return localId.toInt()
    }
}
