package com.ankurupadhyay.salemanagement.data.request

import com.ankurupadhyay.salemanagement.data.Order

data class SyncOrderRequest(val userid:String?,val orders: List<Order>)