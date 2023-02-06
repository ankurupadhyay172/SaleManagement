package com.ankurupadhyay.salemanagement.ui.replacement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.CartsAdapter
import com.ankurupadhyay.salemanagement.adapters.ReplacementCartsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.databinding.FragmentCartBinding
import com.ankurupadhyay.salemanagement.databinding.FragmentReplacementCartBinding
import com.ankurupadhyay.salemanagement.ui.HomeViewModel
import com.ankurupadhyay.salemanagement.utils.setPrice
import com.ankurupadhyay.salemanagement.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReplacementCartFragment:BaseFragment<FragmentReplacementCartBinding, ReplacementViewModel>() {

    val homeViewModel:ReplacementViewModel by activityViewModels()
    @Inject
    lateinit var adapter: ReplacementCartsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter

        getViewDataBinding().checkout.setOnClickListener {
            //findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckoutFragment())
            findNavController().navigate(ReplacementCartFragmentDirections.actionReplacementCartFragmentToReplacementCheckoutFragment())
        }

        homeViewModel.getCartFlow().observe(viewLifecycleOwner){
            if(it.isEmpty()){
                getViewDataBinding().checkout.visibility = View.GONE
            }
            adapter.submitList(it)
            val total = it.sumOf { it.variants.sellingPrice*it.variants.selectedQuan }
            getViewDataBinding().totalPrice.setPrice(total)
        }

        adapter.delete = {
            homeViewModel.deleteSingleCart(it!!)
            showToast("Item Deleted Successfully")
        }
        adapter.update = {id,quan->
            if (quan>0)
                homeViewModel.updateCartQua(id,quan)
            else
                showToast("Quantity can't be less than 1")
        }
        adapter.error ={
            showErrorDialog(requireContext(),layoutInflater,getString(R.string.out_of_stock_error)).show()
        }
    }




    override fun getLayoutId() = R.layout.fragment_replacement_cart

    override fun getViewModel() = homeViewModel
}