package com.ankurupadhyay.salemanagement.ui.replacement

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.ReplaceOrdersAdapter
import com.ankurupadhyay.salemanagement.adapters.StocksProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.request.CommonRequestModel
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllProductsBinding
import com.ankurupadhyay.salemanagement.ui.HomeViewModel
import com.ankurupadhyay.salemanagement.utils.showDeleteWarningDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllReplaceOrdersFragment:BaseFragment<FragmentAllProductsBinding, ReplacementViewModel>() {

    val homeViewModel: ReplacementViewModel by viewModels()

    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var adapter:ReplaceOrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter

        homeViewModel.getOrdersFlow().observe(viewLifecycleOwner){
            getViewDataBinding().isEmpty = it.isEmpty()
            adapter.submitList(it)
        }

        getViewDataBinding().addProduct.setOnClickListener {
            findNavController().navigate(AllReplaceOrdersFragmentDirections.actionAllReplaceOrdersFragmentToAddReplacementFragment())
        }


    }


    override fun getLayoutId() = R.layout.fragment_all_products

    override fun getViewModel() = homeViewModel
}