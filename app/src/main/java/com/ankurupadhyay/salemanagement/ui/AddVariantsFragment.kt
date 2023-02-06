package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.StocksProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.Products
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentCreateVarientBinding
import com.ankurupadhyay.salemanagement.utils.Common
import com.ankurupadhyay.salemanagement.utils.QueryType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddVariantsFragment : BaseFragment<FragmentCreateVarientBinding, HomeViewModel>() {

    val homeViewModel: HomeViewModel by viewModels()

    private val args: AddVariantsFragmentArgs by navArgs()

    lateinit var type:String
    lateinit var myId:String

    @Inject
    lateinit var adapter: StocksProductsAdapter
    @Inject
    lateinit var databaseManager: DatabaseManager
    private lateinit var product: Products
    var serverId:Int=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        type = if(args.vid=="na")  QueryType.ADD.type else QueryType.UPDATE.type
        CoroutineScope(Dispatchers.IO).launch {
            product = databaseManager.getSingleProduct(args.pid)!!
            if(args.vid=="na")
            myId = Common.generateId(System.currentTimeMillis(),databaseManager.getUsers()!!.id)
        }
        getViewDataBinding().callback = this
        handleUpdate()
    }

    private fun handleUpdate() {
        if (args.vid != "na") {
            homeViewModel.getSingleVariant(args.vid).observe(viewLifecycleOwner) {
                getViewDataBinding().model = it
                myId = it!!.id
            }
        }else{
            getViewDataBinding().quan.setText("")
            getViewDataBinding().pPrice.setText("")
            getViewDataBinding().price.setText("")
        }
    }

    fun onSubmitClick() {

            if (Common.isEmpty(getViewDataBinding().name) ||
                Common.isEmpty(getViewDataBinding().pPrice) ||
                Common.isEmpty(getViewDataBinding().description) ||
                Common.isEmpty(getViewDataBinding().price) ||
                Common.isEmpty(getViewDataBinding().quan)
            ) {
                if (Common.isEmpty(getViewDataBinding().name))
                    getViewDataBinding().name.error = "Please enter variant name"

                if (Common.isEmpty(getViewDataBinding().pPrice))
                    getViewDataBinding().pPrice.error = "Please enter purchasing price"

                if (Common.isEmpty(getViewDataBinding().description))
                    getViewDataBinding().description.error = "Please enter product description"

                if (Common.isEmpty(getViewDataBinding().price))
                    getViewDataBinding().pPrice.error = "Please enter selling price"

                if (Common.isEmpty(getViewDataBinding().quan))
                    getViewDataBinding().quan.error = "Please enter product quantity"
            } else {
                if (args.vid == "na") {
                    databaseManager.addVariant(getAllEntry())
                    databaseManager.updateProductStatus(args.pid,QueryType.UPDATE.type)
                    showToast("Variant added successfully")
                } else {
                    homeViewModel.updateVariant(getAllEntry())
                    databaseManager.updateProductStatus(args.pid,QueryType.UPDATE.type)
                    showToast("Variant updated successfully")

                }
                findNavController().popBackStack()
            }

    }

    private fun getAllEntry(): Variants {

        return Variants(
            id=myId,
            name = getViewDataBinding().name.text.toString(),
            description = getViewDataBinding().description.text.toString(),
            acPrice = getViewDataBinding().pPrice.text.toString().toDouble(),
            sellingPrice = getViewDataBinding().price.text.toString().toDouble(),
            quantity = getViewDataBinding().quan.text.toString().toInt(),
            pid = args.pid,
            pName = product.name,
            selectedQuan = 0,
            type = type
        )
    }

    override fun getLayoutId() = R.layout.fragment_create_varient

    override fun getViewModel() = homeViewModel
}