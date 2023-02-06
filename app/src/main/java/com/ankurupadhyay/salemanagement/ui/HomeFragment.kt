package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.data.request.SyncOrderRequest
import com.ankurupadhyay.salemanagement.data.request.SyncReplaceOrderRequest
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentHomeBinding
import com.ankurupadhyay.salemanagement.worker.ReceiveSyncDataWorker
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding,HomeViewModel>() {
    val homeViewModel:HomeViewModel by viewModels()
    @Inject
    lateinit var databaseManager: DatabaseManager
    lateinit var list: List<Order>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getAllOrders().observe(viewLifecycleOwner){
            list = it
            Log.d("mylogorders", "onViewCreated: $list\nDate "+Calendar.getInstance().time)
        }

       getViewDataBinding().addProduct.setOnClickListener {
           findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllProductsFragment())
       }

        getViewDataBinding().addSale.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllSaleProductsFragment())
        }
        getViewDataBinding().todaySale.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAnalyticsFragment())
        }
        getViewDataBinding().viewCart.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCartFragment())
        }

        getViewDataBinding().addReplacement.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllReplaceOrdersFragment())
        }

        val manager = WorkManager.getInstance(requireActivity())
        val data = Data.Builder()
        homeViewModel.getAllProducts().observe(viewLifecycleOwner){
            data.put("products",Gson().toJson(it))
        }



    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getViewModel() = homeViewModel
}