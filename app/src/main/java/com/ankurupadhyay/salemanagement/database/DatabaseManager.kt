package com.ankurupadhyay.salemanagement.database

import com.ankurupadhyay.salemanagement.data.*
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DatabaseManager {

    fun addUser(user: User?)
    suspend fun getUsers():User?
    fun updateUser(date: Date)
    fun deleteUser()
    fun addProduct(products: Products)
    fun updateProductStatus(id: String,type: String)
    fun addVariant(model:Variants)
    fun updateVariant(model: Variants)
    fun deleteSingleVariant(id:String)
    fun deleteSingleProduct(id: String)
    fun updateProductStock(id: Int,quan:Int)
    fun updateProductName(id:String,name:String,type:String)
    suspend fun getSingleProduct(id:String):Products?
    suspend fun getSingleVariant(id:String):Variants?
    suspend fun getProductWithVariants():List<Products>?
    fun updateDiscount(id:String,discount:Double)
    suspend fun getProducts():List<Products>?
    suspend fun getProductsFlow():Flow<List<Products>>?
    suspend fun getQueryTypeProduct():List<Products>?
    fun updateProductVariant(id:String,list: List<Variants>)
    fun getProductVariants(id:String):Flow<List<Variants>>
    suspend fun getVariantsWithProductId(id: String):List<Variants>
    fun updateVariantProductName(pname:String,id:String)
    suspend fun updateCartQun(id:String,quan: Int)
    suspend fun updateReplaceCartQun(id:String,quan: Int)
    fun addToCart(myCart: MyCart)
    suspend fun readAllCart():List<MyCart>?
    suspend fun getCartFlow():Flow<List<MyCart>>
    fun updateVariantQuan(id:String,quan: Int)
    fun deleteSingleCart(id:String)
    fun clearCart()
    fun updateCart(myCart: MyCart)
    fun addOrder(order: Order)
    fun addReplaceOrder(order: ReplaceOrder)
    suspend fun getAllReplaceOrders():List<ReplaceOrder>
    suspend fun getAllOrders():List<Order>
    suspend fun getOrderByDate(today:Date):Flow<List<Order>>
    suspend fun getOrderByMonth(today: Date):Flow<List<Order>>
    suspend fun getOrderByYear(today: Date):Flow<List<Order>>
    fun addToReplacementCart(myCart: ReplaceCart)
    suspend fun getReplacementCartFlow():Flow<List<ReplaceCart>>
    fun deleteSingleReplacementCart(id:String)
    fun clearReplacementCart()
}