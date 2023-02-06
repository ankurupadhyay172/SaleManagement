package com.ankurupadhyay.salemanagement.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ankurupadhyay.salemanagement.data.*

@Database(entities = [User::class,Products::class,MyCart::class,Order::class,Variants::class,ReplaceCart::class,ReplaceOrder::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class MyDatabase:RoomDatabase() {
    abstract fun getMyDatabaseDao():MyDao
}