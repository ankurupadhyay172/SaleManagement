package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.CartsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.databinding.FragmentCartBinding
import com.ankurupadhyay.salemanagement.utils.setPrice
import com.ankurupadhyay.salemanagement.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment:BaseFragment<FragmentCartBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by activityViewModels()
    @Inject
    lateinit var adapter:CartsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
        getCartData()

        getViewDataBinding().checkout.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckoutFragment())
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

    private fun getCartData() {
        homeViewModel.getCartFlow().observe(viewLifecycleOwner){ it ->
            adapter.submitList(it)
            homeViewModel.cartTotal = it.sumOf { it.variants.sellingPrice * it.quan }
            getViewDataBinding().totalPrice.setPrice(homeViewModel.cartTotal)
            getViewDataBinding().cartPrice = homeViewModel.cartTotal
            if (it.isNotEmpty()) {
                    getViewDataBinding().discount.text = homeViewModel.discount.toString()
                    getViewDataBinding().discount.setPrice(homeViewModel.discount)
                    homeViewModel.grandTotal = homeViewModel.cartTotal- homeViewModel.discount
                    getViewDataBinding().isEmpty = false
            }else{
                    getViewDataBinding().isEmpty = true
            }

            getViewDataBinding().gTotal =homeViewModel.grandTotal

            getViewDataBinding().edit.setOnClickListener {

                    findNavController().navigate(CartFragmentDirections.actionCartFragmentToAddDiscountFragment(
                        homeViewModel.discount.toLong()))
                }
        }
    }


    override fun getLayoutId() = R.layout.fragment_cart

    override fun getViewModel() = homeViewModel
}