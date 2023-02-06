package com.ankurupadhyay.salemanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class MyCart(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val variants: Variants,
    val quan:Int=0,
    val discount:Double=0.0
)