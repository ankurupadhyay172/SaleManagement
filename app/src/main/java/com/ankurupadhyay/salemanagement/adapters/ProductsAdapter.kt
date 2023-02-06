package com.ankurupadhyay.salemanagement.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseListAdapter
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.databinding.ItemProductListBinding

import javax.inject.Inject

class ProductsAdapter @Inject constructor():BaseListAdapter<Products,ItemProductListBinding>(DiffCallback()) {

    var add:((id:Products?,quan:Int)->Unit)?=null
    var open:((id:String,varient:Variants,quan:Int)->Unit)?=null
    var error:(()->Unit)?=null
    lateinit var context: Context

class DiffCallback:DiffUtil.ItemCallback<Products>()
{

    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem==newItem
    }

}

    override fun createBinding(parent: ViewGroup): ItemProductListBinding {
        context = parent.context
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.item_product_list,parent,false)

    }

    override fun bind(binding: ItemProductListBinding, item: Products?) {
        binding.model = item

        item?.list?.let {
            binding.quan = 0

            val adapter = SpinnerAdapter(context,R.layout.item_variant_layout,it)
            binding.variant = item.setSelectedDefault()
            binding.selectVariant.setOnClickListener {
                //open?.invoke(item!!.id)
                AlertDialog.Builder(context).setTitle("Select Variant")
                    .setAdapter(adapter) { dialoge, position ->

                    binding.variant = item.setSelectedDefault(position)
                        try {
                            binding.quan = item.selectedVariant!!.selectedQuan
                            binding.txtCount.text = item.selectedVariant!!.selectedQuan.toString()
                        }catch (e:Exception)
                        {
                        }
                    }.create().show()
            }

            binding.txtAddItem.setOnClickListener {
                item.selectedVariant?.let {
                    if (it.quantity<=it.selectedQuan){
                        //Toast.makeText(context, "Product is out of stock", Toast.LENGTH_SHORT).show()
                    error?.invoke()
                    }else{
                        val quan = ++it.selectedQuan
                        binding.quan = quan
                        binding.txtCount.text = quan.toString()
                        open?.invoke(it.id,it,quan)
                    }
                }
            }
            binding.liAdd.setOnClickListener {
                item.selectedVariant?.let {
                    if (it.quantity<=it.selectedQuan){
                        error?.invoke()

                    }else{
                        val quan = ++it.selectedQuan
                        binding.quan = quan
                        binding.txtCount.text = quan.toString()
                        open?.invoke(it.id,item.selectedVariant!!,quan)
                    }
                }
            }

            binding.liSub.setOnClickListener {
                val quan = --item.selectedVariant!!.selectedQuan
                binding.quan = quan
                binding.txtCount.text = quan.toString()
                open?.invoke(item.selectedVariant!!.id,item.selectedVariant!!,quan)
            }
        }



    }

}