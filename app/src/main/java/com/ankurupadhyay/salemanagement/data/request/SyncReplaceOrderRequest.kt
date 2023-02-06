package com.ankurupadhyay.salemanagement.data.request

import com.ankurupadhyay.salemanagement.data.ReplaceOrder

data class SyncReplaceOrderRequest(val userid:String?, val orders: List<ReplaceOrder>)