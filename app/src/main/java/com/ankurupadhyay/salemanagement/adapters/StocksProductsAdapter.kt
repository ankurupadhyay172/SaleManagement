package com.ankurupadhyay.salemanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseListAdapter
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.databinding.ItemProductBinding
import com.ankurupadhyay.salemanagement.databinding.ItemProductListBinding
import com.ankurupadhyay.salemanagement.databinding.ItemStockItemBinding
import javax.inject.Inject

class StocksProductsAdapter @Inject constructor():BaseListAdapter<Products,ItemProductBinding>(DiffCallback()) {

    var add:((id:Products?,quan:Int)->Unit)?=null
    var quan=0
    var open:((id:Products?)->Unit)?=null
    var edit:((id:Products?)->Unit)?=null
    var delete:((id:String?)->Unit)?=null

class DiffCallback:DiffUtil.ItemCallback<Products>()
{

    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem==newItem
    }

}

    override fun createBinding(parent: ViewGroup): ItemProductBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.item_product,parent,false)
    }

    override fun bind(binding: ItemProductBinding, item: Products?) {
        binding.model = item

        binding.cardView.setOnClickListener {
            open?.invoke(item)
        }
        binding.tvEdit.setOnClickListener {
            edit?.invoke(item)
        }
        binding.tvDelete.setOnClickListener {
            delete?.invoke(item?.id)
        }
    }

}