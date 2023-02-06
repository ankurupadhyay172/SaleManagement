package com.ankurupadhyay.salemanagement.database

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.room.Room
import com.ankurupadhyay.salemanagement.data.*
import com.ankurupadhyay.salemanagement.utils.Common
import com.ankurupadhyay.salemanagement.utils.QueryType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DatabaseManagerImpl @Inject constructor(@ApplicationContext context:Context):DatabaseManager {

    val db = Room.databaseBuilder(context,MyDatabase::class.java,Common.DATABASE_NAME).build()

    override fun addUser(user: User?) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addUser(user)
        }
    }

    override suspend fun getUsers(): User? {
      return if(db.getMyDatabaseDao().getUsers().isEmpty()) null else db.getMyDatabaseDao().getUsers()[0]
    }

    override fun updateUser(date: Date) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateUser(date)
        }
    }

    override fun deleteUser() {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().deleteUser()
        }
    }

    override fun addProduct(products: Products) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addProduct(products)
        }
    }

    override fun updateProductStatus(id: String,type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateProductStatus(id,type)
        }
    }

    override fun addVariant(model: Variants) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addProductVariant(model)
        }
    }

    override fun updateVariant(model: Variants) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateVariant(model)
        }
    }

    override fun deleteSingleVariant(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().deleteSingleVariant(id)
        }
    }

    override fun deleteSingleProduct(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().deleteSingleProduct(id)
        }
    }

    override fun updateProductStock(id: Int,quan:Int) {
        CoroutineScope(Dispatchers.IO).launch {
         //   db.getMyDatabaseDao().updateProductStock(id,quan)
        }
    }

    override fun updateProductName(id: String, name: String,type:String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateProductName(id,name,type)
        }
    }

    override suspend fun getSingleProduct(id: String): Products? {
        return db.getMyDatabaseDao().getSingleProduct(id)
    }

    override suspend fun getSingleVariant(id: String): Variants? {
        return db.getMyDatabaseDao().getSingleVariant(id)
    }

    override suspend fun getProductWithVariants(): List<Products>? {
        return db.getMyDatabaseDao().getProductsWithVariants()
    }

    override fun updateDiscount(id: String, discount: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateDiscount(id,discount)
        }
    }

    override suspend fun getProducts(): List<Products>? {
        return db.getMyDatabaseDao().getProducts()
    }

    override suspend fun getProductsFlow(): Flow<List<Products>>? {
        return db.getMyDatabaseDao().getProductsFlow()
    }

    override suspend fun getQueryTypeProduct(): List<Products>? {
        return db.getMyDatabaseDao().getQueryTypeProducts()
    }

    override fun updateProductVariant(id: String, list: List<Variants>) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateProductVariant(id,list)
        }
    }

    override fun getProductVariants(id:String): Flow<List<Variants>> {
        return db.getMyDatabaseDao().getProductVariants(id)
    }

    override suspend fun getVariantsWithProductId(id:String): List<Variants> {
        return db.getMyDatabaseDao().getVariantsWithProductId(id)
    }

    override fun updateVariantProductName(pname: String, id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateVariantProduct(pname,id)
        }
    }

    override suspend fun updateCartQun(id: String, quan: Int) {
        db.getMyDatabaseDao().updateCartQun(id,quan)
    }

    override suspend fun updateReplaceCartQun(id: String, quan: Int) {
        db.getMyDatabaseDao().updateReplaceCartQun(id,quan)
    }

    override fun addToCart(myCart: MyCart) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addToCart(myCart)
        }
    }

    override suspend fun readAllCart(): List<MyCart>? {
        return db.getMyDatabaseDao().getAllCart()
    }

    override suspend fun getCartFlow(): Flow<List<MyCart>> {
        return db.getMyDatabaseDao().getCartFlow()
    }

    override fun updateVariantQuan(id: String, quan: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().updateVariantQuan(id,quan,QueryType.UPDATE.type)
        }
    }

    override fun deleteSingleCart(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().deleteSingleCart(id)
        }
    }

    override fun clearCart() {
        db.getMyDatabaseDao().clearCart()
    }

    override fun updateCart(myCart: MyCart) {
        TODO("Not yet implemented")
    }

    override  fun addOrder(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addOrder(order)
        }
    }

    override fun addReplaceOrder(order: ReplaceOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addReplaceOrder(order)
        }
    }

    override suspend fun getAllReplaceOrders(): List<ReplaceOrder> {
        return db.getMyDatabaseDao().getAllReplaceOrders()
    }

    override suspend fun getAllOrders(): List<Order> {
        return db.getMyDatabaseDao().getAllOrders()
    }

    override suspend fun getOrderByDate(today:Date): Flow<List<Order>> {
        return db.getMyDatabaseDao().getAllOrdersByDate(today)
    }

    override suspend fun getOrderByMonth(today: Date): Flow<List<Order>> {
        val format = SimpleDateFormat("yyyy").format(today)
        val start = Calendar.getInstance()
        start.set(format.toInt(),today.month,1)
        val end = Calendar.getInstance()
        val endDay = Common.lastDayOfMonth(format.toInt(),today.month+1)
        end.set(format.toInt(),today.month,endDay+1)
        Log.d("mylogdate", "getOrderByMonth: start : ${start.time}\nend : ${end.time}")
        return db.getMyDatabaseDao().getAllOrdersByMonth(start.time.time,end.time.time)
    }

    override suspend fun getOrderByYear(today: Date): Flow<List<Order>> {
        return db.getMyDatabaseDao().getAllOrdersByYear(today)
    }

    override fun addToReplacementCart(myCart: ReplaceCart) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addToReplacementCart(myCart)
        }
    }

    override suspend fun getReplacementCartFlow(): Flow<List<ReplaceCart>> {
        return db.getMyDatabaseDao().getAllReplacementCartFlow()
    }

    override fun deleteSingleReplacementCart(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().deleteSingleReplacementCart(id)
        }
    }

    override fun clearReplacementCart() {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().clearReplaceCart()
        }
    }
}