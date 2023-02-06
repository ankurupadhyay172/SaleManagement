package com.ankurupadhyay.salemanagement.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

interface AdapterBindingHelper<T,V:ViewDataBinding>{

    //to create viewBinding for particular view type
    //@param parent ViewGroup
    //@return V viewBinding


    fun createBinding(parent:ViewGroup):V


    /*Called when you have to bind view with item
    @param binding viewbinding of view
    @param item to be bind with viewBinding*/


    fun bind(binding:V,item:T?)

    fun bind(binding: V,item: T?,position:Int)

}