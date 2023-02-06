package com.ankurupadhyay.salemanagement.database

import androidx.room.*
import com.ankurupadhyay.salemanagement.data.*
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp
import java.util.*

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(model:User?)

    @Query("select * from user")
    suspend fun getUsers():List<User>

    @Query("update user SET timestamp=:timestamp")
    suspend fun updateUser(timestamp:Date)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(model:Products?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductVariant(model:Variants?)

    @Update
    suspend fun updateVariant(variants: Variants)

    @Query("delete from Variants where id=:id")
    suspend fun deleteSingleVariant(id:String)

    @Query("delete from User")
    suspend fun deleteUser()

    @Query("delete from Products where id=:id")
    suspend fun deleteSingleProduct(id:String)

    @Query("select * from Variants where pid=:id")
    fun getProductVariants(id: String):Flow<List<Variants>>

    @Query("select * from Variants where pid=:id")
    suspend fun getVariantsWithProductId(id: String):List<Variants>

    @Query("select * from Variants where id=:id")
    suspend fun getSingleVariant(id:String):Variants?

    @Query("update Variants SET quantity=:quan,type=:type where id=:id")
    suspend fun updateVariantQuan(id:String,quan:Int,type:String)

    @Query("update Variants SET pName=:pname where id=:id")
    suspend fun updateVariantProduct(pname: String,id :String)

    @Query("update Products SET type=:type where id=:id")
    suspend fun updateProductStatus(id:String,type:String)

    @Query("update Products SET name=:name,type=:type where id=:id")
    suspend fun updateProductName(id:String,name:String,type:String)

    @Query("update MyCart SET quan=:qun where id=:id")
    suspend fun updateCartQun(id:String,qun:Int)

    @Query("update ReplaceCart SET quan=:qun where id=:id")
    suspend fun updateReplaceCartQun(id:String,qun:Int)

    @Query("select * from Products")
    suspend fun getProducts():List<Products>?

    @Query("select * from Products")
    fun getProductsFlow():Flow<List<Products>>?

    @Query("select * from Products where type='add' Or type='update'")
    fun getQueryTypeProducts():List<Products>?

    @Query("select * from Products where id=:id")
    suspend fun getSingleProduct(id:String):Products?

    @Query("select * from Products inner join Variants on Products.id=Variants.pid")
    suspend fun getProductsWithVariants():List<Products>?

    @Query("update Products SET list=:list where id=:id")
    suspend fun updateProductVariant(id:String,list:List<Variants>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(myCart: MyCart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToReplacementCart(myCart: ReplaceCart)

    @Query("select * from ReplaceCart")
    fun getAllReplacementCartFlow():Flow<List<ReplaceCart>>

    @Query("delete from ReplaceCart where id=:id")
    suspend fun deleteSingleReplacementCart(id:String)

    @Query("delete from ReplaceCart")
    fun clearReplaceCart()

    @Query("update MyCart SET discount=:discount where id=:id")
    suspend fun updateDiscount(id:String,discount:Double)

    @Query("select * from mycart")
    suspend fun getAllCart():List<MyCart>

    @Query("select * from mycart")
    fun getCartFlow():Flow<List<MyCart>>

    @Query("delete from mycart where id=:id")
    suspend fun deleteSingleCart(id:String)

    @Query("delete from mycart")
    fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(order: Order)

    @Query("select * from `order`")
    suspend fun getAllOrders():List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReplaceOrder(order: ReplaceOrder)

    @Query("select * from ReplaceOrder")
    suspend fun getAllReplaceOrders():List<ReplaceOrder>

//    @Query("select * from `order` where timestamp between date(:from) And date(:to)")
//    suspend fun getAllOrdersByDate(from:Date,to:String):List<Order>


    @Query("select * from `order` where strftime('%d',datetime(timestamp/1000, 'unixepoch'))= strftime('%d',datetime(:today/1000, 'unixepoch')) order by timestamp DESC")
    fun getAllOrdersByDate(today:Date):Flow<List<Order>>

    @Query("SELECT * FROM `order` where timestamp between :start and :end order by timestamp DESC")
    fun getAllOrdersByMonth(start: Long,end: Long):Flow<List<Order>>

    @Query("SELECT * FROM `order` WHERE datetime(datetime(timestamp/1000, 'unixepoch'),'start of year') = datetime(:today/1000,'unixepoch','start of year') order by timestamp DESC")
    fun getAllOrdersByYear(today:Date):Flow<List<Order>>
}