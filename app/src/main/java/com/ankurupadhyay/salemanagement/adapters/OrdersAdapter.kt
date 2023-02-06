package com.ankurupadhyay.salemanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseListAdapter
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.databinding.ItemCartBinding
import com.ankurupadhyay.salemanagement.databinding.ItemOrderBinding
import com.ankurupadhyay.salemanagement.utils.setPrice
import javax.inject.Inject

class OrdersAdapter @Inject constructor() :
    BaseListAdapter<Order, ItemOrderBinding>(DiffCallback()) {

    var add: ((id: Products?, quan: Int) -> Unit)? = null
    var quan = 0

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup): ItemOrderBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order, parent, false
        )
    }

    override fun bind(binding: ItemOrderBinding, item: Order?) {
        binding.subtitle.text = "Name : " + item?.customerName
        binding.txtContact.text = "Contact No : " + item?.contactNo
        binding.txtTotalItems.text = "Total Items : " + item?.items?.size
        binding.txtPaymentMethod.text = "Payment Method : " + getPaymentType(item?.paymentType)
        val total = item?.items?.sumOf { it.variants.sellingPrice * it.variants.selectedQuan }!!
        val discount = item.discount
        binding.txtGrandTotal.setPrice(total - discount)
        binding.discount.text = "Discount = ₹ $discount"
        binding.txtOrderDate.text = "Date  = ${item.timestampToDate()}"

        var s = ""
        for (i in item.items) {
            s += "(" + i.variants.pName + ")" + i.variants.name + "(${i.variants.sellingPrice}) X " + i.variants.selectedQuan + " = " + i.variants.sellingPrice * i.variants.selectedQuan + "\n"
        }
        binding.txtAllItems.text = s

    }

    fun getPaymentType(type: Int?): String {
        return if (type == 1) "Cash" else "Online"
    }

}