package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.StocksProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.request.CommonRequestModel
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllProductsBinding
import com.ankurupadhyay.salemanagement.utils.showDeleteWarningDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllProductsFragment:BaseFragment<FragmentAllProductsBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by viewModels()
    @Inject
    lateinit var adapter: StocksProductsAdapter
    @Inject
    lateinit var databaseManager: DatabaseManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.getAllProducts().observe(viewLifecycleOwner){
            Log.d("allproducts1", "onViewCreated: $it")
            it?.let {
                for (i in it){
                   homeViewModel.getProductVariantFlow(i.id).observe(viewLifecycleOwner){
                       homeViewModel.updateProductVariantList(i.id,it)
                   }
                }
            }
        }

        getViewDataBinding().recyclerView.adapter = adapter
        getViewDataBinding().addProduct.setOnClickListener {
        findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToCreateProduct())
        }
        homeViewModel.getProductsFlow().observe(viewLifecycleOwner){
            adapter.submitList(it)
            getViewDataBinding().isEmpty = it.isEmpty()
        }
        adapter.open = {
            findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToAllVariantsFragment(it!!.id,it.name))
        }

        adapter.edit = {
            findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToCreateProduct(it!!.id))
        }

        adapter.delete = {id->
            showDeleteWarningDialog(requireContext(),layoutInflater,getString(R.string.del_product_error)) {
                showLoading(true)
                homeViewModel.deleteProduct(CommonRequestModel(id)).observe(viewLifecycleOwner){
                    it.getValueOrNull()?.let {
                        if(it.status==1)
                            showToast("Product deleted successfully")

                        homeViewModel.deleteProduct(id!!)
                        showLoading(false)
                    }
                }
            }.show()

        }
    }


    override fun getLayoutId() = R.layout.fragment_all_products

    override fun getViewModel() = homeViewModel
}