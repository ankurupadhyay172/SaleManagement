package com.ankurupadhyay.salemanagement.ui.replacement

import androidx.lifecycle.liveData
import com.ankurupadhyay.salemanagement.base.BaseViewModel
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.data.ReplaceCart
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplacementViewModel @Inject constructor(val databaseManager: DatabaseManager):BaseViewModel() {

    fun addToCart(id: String, variants: Variants, qua: Int) {
        databaseManager.addToReplacementCart(ReplaceCart(id,variants,qua))
    }

    fun deleteSingleCart(id: String) {
        databaseManager.deleteSingleReplacementCart(id)
    }

    fun getCartFlow()  = liveData(Dispatchers.IO){
        databaseManager.getReplacementCartFlow().collect{
            emit(it)
        }
    }

    fun getOrdersFlow()  = liveData(Dispatchers.IO){
        emit(databaseManager.getAllReplaceOrders())
    }

    fun updateCartQua(id: String,qua: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseManager.updateReplaceCartQun(id,qua)
        }
    }


}