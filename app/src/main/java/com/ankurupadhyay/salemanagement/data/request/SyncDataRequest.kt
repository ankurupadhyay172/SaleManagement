package com.ankurupadhyay.salemanagement.data.request

import com.ankurupadhyay.salemanagement.data.Variants

data class SyncDataRequest(val id:String,val name:String?,val variants:List<Variants>?)