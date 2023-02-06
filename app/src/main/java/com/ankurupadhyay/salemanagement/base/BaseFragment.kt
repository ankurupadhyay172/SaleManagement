package com.ankurupadhyay.salemanagement.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ankurupadhyay.salemanagement.utils.autoCleared

abstract class BaseFragment<T:ViewDataBinding,V:BaseViewModel>:Fragment() {
    private var mViewDataBinding by autoCleared<T>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = DataBindingUtil.inflate<T>(inflater,getLayoutId(),container,false).also {
        mViewDataBinding = it
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        mViewDataBinding.executePendingBindings()
    }.root

    fun showToast(message:String)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    abstract fun getLayoutId():Int

    abstract fun getViewModel():V

    fun getViewDataBinding():T{
        return mViewDataBinding
    }

    fun showLoading(visible:Boolean){
        if (visible)
            (requireActivity() as BaseActivity).showLoading(visible)
        else
            (requireActivity() as BaseActivity).hideLoading()
    }
}