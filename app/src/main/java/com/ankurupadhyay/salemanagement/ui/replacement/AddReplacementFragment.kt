package com.ankurupadhyay.salemanagement.ui.replacement

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.ProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllSaleProductsBinding
import com.ankurupadhyay.salemanagement.ui.HomeViewModel
import com.ankurupadhyay.salemanagement.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddReplacementFragment:BaseFragment<FragmentAllSaleProductsBinding, ReplacementViewModel>() {

    val homeViewModel:ReplacementViewModel by viewModels()
    @Inject
    lateinit var adapter:ProductsAdapter
    @Inject
    lateinit var databaseManager: DatabaseManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewDataBinding().recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch  {
            val list = databaseManager.getProducts()
            adapter.submitList(list)
            getViewDataBinding().isEmpty = list?.isEmpty()
            Log.d("mylogresult",""+databaseManager.getProducts())
        }
            updateCart()

            adapter.open ={id,varient,quan->
                if (quan>0) {
                    homeViewModel.addToCart(id,varient,quan)
                }else{
                    homeViewModel.deleteSingleCart(id)
                }
        }
            adapter.error ={
                showErrorDialog(requireContext(),layoutInflater,getString(R.string.out_of_stock_error)).show()
            }

        getViewDataBinding().viewCart.setOnClickListener {
            //findNavController().navigate(AllSaleProductsFragmentDirections.actionAllSaleProductsFragmentToCartFragment())
            findNavController().navigate(AddReplacementFragmentDirections.actionAddReplacementFragmentToReplacementCartFragment())
        }

    }

    fun updateCart() {
        homeViewModel.getCartFlow().observe(viewLifecycleOwner){
            getViewDataBinding().total = it.size
            getViewDataBinding().cartTitle.text = "Cart have "+it.size+" item"
        }
    }


    override fun getLayoutId() = R.layout.fragment_all_sale_products

    override fun getViewModel() = homeViewModel
}