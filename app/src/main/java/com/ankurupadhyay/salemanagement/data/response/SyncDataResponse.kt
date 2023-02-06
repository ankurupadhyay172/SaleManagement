package com.ankurupadhyay.salemanagement.data.response

import com.ankurupadhyay.salemanagement.data.Variants


data class SyncDataResponse(val status:Int,val result:List<SyncResultData>)

data class SyncResultData(val pid:Int,val name:String,val lastSync:String,val variants:List<Variants>)

