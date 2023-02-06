package com.ankurupadhyay.salemanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseListAdapter
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.databinding.ItemProductBinding
import com.ankurupadhyay.salemanagement.databinding.ItemProductListBinding
import com.ankurupadhyay.salemanagement.databinding.ItemStockItemBinding
import javax.inject.Inject

class ProductVariantsAdapter @Inject constructor():BaseListAdapter<Variants,ItemStockItemBinding>(DiffCallback()) {
    var edit:((id:String?)->Unit)?=null
    var delete:((id:String?)->Unit)?=null


class DiffCallback:DiffUtil.ItemCallback<Variants>()
{
    override fun areItemsTheSame(oldItem: Variants, newItem: Variants): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Variants, newItem: Variants): Boolean {
        return oldItem==newItem
    }
}

    override fun createBinding(parent: ViewGroup): ItemStockItemBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.item_stock_item,parent,false)
    }

    override fun bind(binding: ItemStockItemBinding, item: Variants?) {
        binding.model = item
        binding.stockQuan.text = "Quan Left = ${item?.quantity}"
        binding.pPrice.text = "Purchasing Price = ₹${item?.acPrice}"
        binding.price.text = "Selling Price = ₹${item?.sellingPrice}"
        binding.txtEditItem.setOnClickListener {
            edit?.invoke(item?.id)
        }
        binding.txtDeleteItem.setOnClickListener {
            delete?.invoke(item?.id)
        }
    }

}