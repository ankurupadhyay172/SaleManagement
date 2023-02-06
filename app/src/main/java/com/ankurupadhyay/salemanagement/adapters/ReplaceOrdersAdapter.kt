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
import com.ankurupadhyay.salemanagement.data.ReplaceOrder
import com.ankurupadhyay.salemanagement.databinding.ItemCartBinding
import com.ankurupadhyay.salemanagement.databinding.ItemOrderBinding
import com.ankurupadhyay.salemanagement.databinding.ItemReplaceOrderBinding
import com.ankurupadhyay.salemanagement.utils.setPrice
import javax.inject.Inject

class ReplaceOrdersAdapter @Inject constructor() :
    BaseListAdapter<ReplaceOrder, ItemReplaceOrderBinding>(DiffCallback()) {

    var add: ((id: Products?, quan: Int) -> Unit)? = null
    var quan = 0

    class DiffCallback : DiffUtil.ItemCallback<ReplaceOrder>() {
        override fun areItemsTheSame(oldItem: ReplaceOrder, newItem: ReplaceOrder): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ReplaceOrder, newItem: ReplaceOrder): Boolean {
            return oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup): ItemReplaceOrderBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_replace_order, parent, false
        )
    }

    override fun bind(binding: ItemReplaceOrderBinding, item: ReplaceOrder?) {
        binding.subtitle.text = "Replacer Name : " + item?.customerName
        binding.txtContact.text = "Description : " + item?.contactNo
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