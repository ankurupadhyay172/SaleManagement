package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllProductsBinding
import com.ankurupadhyay.salemanagement.databinding.FragmentOrderCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrderCompleteFragment:BaseFragment<FragmentOrderCompleteBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by viewModels()
    @Inject
    lateinit var databaseManager: DatabaseManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateQuantityofProducts()
    }

    private fun updateQuantityofProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = databaseManager.readAllCart()
            list?.let {
                for (i in it)
                {
                    val rQuan = i.variants.quantity-i.variants.selectedQuan
                    databaseManager.updateVariantQuan(i.variants.id,rQuan)
                }
                databaseManager.clearCart()
            }

        }
    }


    override fun getLayoutId() = R.layout.fragment_order_complete

    override fun getViewModel() = homeViewModel
}