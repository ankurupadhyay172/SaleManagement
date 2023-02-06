package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.ProductVariantsAdapter
import com.ankurupadhyay.salemanagement.adapters.ProductsAdapter
import com.ankurupadhyay.salemanagement.adapters.StocksProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.request.CommonRequestModel
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllProductsBinding
import com.ankurupadhyay.salemanagement.utils.showDeleteWarningDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllVariantsFragment:BaseFragment<FragmentAllProductsBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by viewModels()
    @Inject
    lateinit var adapter: ProductVariantsAdapter

    @Inject
    lateinit var databaseManager: DatabaseManager

    val args:AllVariantsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewDataBinding().recyclerView.adapter = adapter
        getViewDataBinding().addProduct.setOnClickListener {
        findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToCreateProduct())
        }
        homeViewModel.getProductVariant(args.pid).observe(viewLifecycleOwner){
             adapter.submitList(it)
            getViewDataBinding().isEmpty = it.isEmpty()
            getViewDataBinding().title.text = "No Variants Found"
            getViewDataBinding().description.text = "Add Variant On Click + Button Below"
        }
        getViewDataBinding().addProduct.setOnClickListener {
            findNavController().navigate(AllVariantsFragmentDirections.actionAllVariantsFragmentToAddVariantsFragment(args.pid))
        }
        adapter.edit = {
            findNavController().navigate(AllVariantsFragmentDirections.actionAllVariantsFragmentToAddVariantsFragment(pid=args.pid,vid=it!!))
        }
        adapter.delete = {id->

            showDeleteWarningDialog(requireContext(),layoutInflater,getString(R.string.del_variant_error)) {
                showLoading(true)
                homeViewModel.deleteProductVariant(CommonRequestModel(id)).observe(viewLifecycleOwner){
                    it.getValueOrNull()?.let {
                        if(it.status==1)
                            showToast("Variant delete successfully")

                        homeViewModel.deleteSingleVariant(id!!)
                        showLoading(false)
                    }
                }
            }.show()

        }
    }


    override fun getLayoutId() = R.layout.fragment_all_products

    override fun getViewModel() = homeViewModel
}