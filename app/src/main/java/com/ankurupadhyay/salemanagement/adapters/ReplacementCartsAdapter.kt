package com.ankurupadhyay.salemanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseListAdapter
import com.ankurupadhyay.salemanagement.data.ReplaceCart
import com.ankurupadhyay.salemanagement.databinding.ItemReplacementCartBinding
import javax.inject.Inject

class ReplacementCartsAdapter @Inject constructor():BaseListAdapter<ReplaceCart,ItemReplacementCartBinding>(DiffCallback()) {

    var update:((id:String,quan:Int)->Unit)?=null
    var delete:((id:String?)->Unit)?=null
    var error:(()->Unit)?=null
    var quan=0


class DiffCallback:DiffUtil.ItemCallback<ReplaceCart>()
{

    override fun areItemsTheSame(oldItem: ReplaceCart, newItem: ReplaceCart): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: ReplaceCart, newItem: ReplaceCart): Boolean {
        return oldItem==newItem
    }

}

    override fun createBinding(parent: ViewGroup): ItemReplacementCartBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.item_replacement_cart,parent,false)
    }

    override fun bind(binding: ItemReplacementCartBinding, item: ReplaceCart?) {
        binding.model = item
        binding.variant = item?.variants
        binding.deleteCart.setOnClickListener {
            delete?.invoke(item?.id)
        }
        var quan = item?.quan!!
        binding.liAdd.setOnClickListener {
            if(item.variants.quantity<=item.variants.selectedQuan){
                error?.invoke()
            }else{
                ++quan
                binding.txtCount.text = quan.toString()
                update?.invoke(item.id,quan)
            }
        }
        binding.liSub.setOnClickListener {

            if (quan>1)
            {
                --quan
                binding.txtCount.text = quan.toString()
                update?.invoke(item.id,quan)
            }else{
                update?.invoke(item.id,0)
            }

        }
    }

}