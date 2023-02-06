package com.ankurupadhyay.salemanagement.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<T,V:ViewDataBinding>(diffCallback:DiffUtil.ItemCallback<T>):
ListAdapter<T,BaseViewHolder<V>>(AsyncDifferConfig.Builder<T>(diffCallback).build()),
    AdapterBindingHelper<T,V>{

    /*called when Recyclerview needs a new ViewHolder of the given type to represent on item
    @param parent ViewGroup The ViewGroup into Which the new View will be added after it is bound
    to and adapter position*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
         val binding = createBinding(parent)
         return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        bind(holder.binding,getItem(position))
        bind(holder.binding,getItem(position),position)
        holder.binding.executePendingBindings()
    }

    abstract override fun createBinding(parent: ViewGroup):V

    override fun bind(binding: V, item: T?) {
       // TODO("Not yet implemented")
    }

    override fun bind(binding: V, item: T?, position: Int) {
       // TODO("Not yet implemented")
    }
}