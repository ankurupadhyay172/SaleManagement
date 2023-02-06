package com.ankurupadhyay.salemanagement.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setprice")
fun TextView.setPrice(price:Double)
{
    this.setText("₹ $price")
}

@BindingAdapter("setquan")
fun TextView.setQuan(quan:Int)
{
    this.text = quan.toString()
}
