package com.ankurupadhyay.salemanagement.utils

import android.content.Context
import android.widget.EditText
import androidx.room.Room
import com.ankurupadhyay.salemanagement.database.MyDatabase
import java.time.LocalDate
import java.time.Year
import java.util.*

class Common {
    companion object{
        val DATABASE_NAME = "mydatabase"

        fun isEmpty(e: EditText):Boolean = e.text.isEmpty()

        fun getRoomDatabase(context: Context): MyDatabase {
            return Room.databaseBuilder(context, MyDatabase::class.java,DATABASE_NAME).build()
        }

        fun lastDayOfMonth(Y: Int, M: Int): Int {
            return LocalDate.of(Y, M, 1).getMonth().length(Year.of(Y).isLeap)
        }
        fun generateId(time: Long,id: Int):String{
            return ""+time+""+id
        }
    }
}