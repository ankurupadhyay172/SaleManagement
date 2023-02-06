package com.ankurupadhyay.salemanagement.data.response

import com.ankurupadhyay.salemanagement.data.Variants


data class SyncReceiveDataResponse(val status:Int,val result:List<SyncDataResult>?)

data class SyncDataResult(val id:String,val name:String,val serverId:Int,val list: List<Variants>?)
