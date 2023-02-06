package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentCreateProductBinding
import com.ankurupadhyay.salemanagement.utils.Common
import com.ankurupadhyay.salemanagement.utils.QueryType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_product.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateProduct:BaseFragment<FragmentCreateProductBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by viewModels()
    val args:CreateProductArgs by navArgs()
    var serverId =0
    lateinit var list: List<Variants>
    @Inject
    lateinit var databaseManager: DatabaseManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.getProductVariant(args.id).observe(viewLifecycleOwner){
            list = it
        }
        homeViewModel.getSingleProduct(args.id).observe(viewLifecycleOwner){
            getViewDataBinding().name.setText(it?.name)
            it?.let {
                serverId = it.serverId
            }
        }
        getViewDataBinding().submit.setOnClickListener {
            if (getViewDataBinding().name.text.isEmpty()) {
                if (getViewDataBinding().name.text.isEmpty())
                    getViewDataBinding().name.error = "Please enter name"

            }else{
                if (args.id=="na"){
                    CoroutineScope(Dispatchers.Main).launch {
                        val id = Common.generateId(System.currentTimeMillis(),databaseManager.getUsers()!!.id)
                        databaseManager.addProduct(Products(id = id, name = getViewDataBinding().name.text.toString(), lastSync = Calendar.getInstance().time, type = QueryType.ADD.type, serverId = serverId ))
                        findNavController().popBackStack()
                    }
                }
                    else{

                    for (i in list){
                        databaseManager.updateVariantProductName(getViewDataBinding().name.text.toString(),i.id)
                    }
                    databaseManager.updateProductName(args.id,getViewDataBinding().name.text.toString(),QueryType.UPDATE.type)
                    showToast("Product Added Successfully")
                    findNavController().popBackStack()
                    }




            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_create_product

    override fun getViewModel()= homeViewModel
}