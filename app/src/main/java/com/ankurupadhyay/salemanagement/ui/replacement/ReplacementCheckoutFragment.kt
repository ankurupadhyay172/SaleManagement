package com.ankurupadhyay.salemanagement.ui.replacement

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.data.ReplaceCart
import com.ankurupadhyay.salemanagement.data.ReplaceOrder
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentCheckoutBinding
import com.ankurupadhyay.salemanagement.databinding.FragmentReplacementCheckoutBinding
import com.ankurupadhyay.salemanagement.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReplacementCheckoutFragment:BaseFragment<FragmentReplacementCheckoutBinding,ReplacementViewModel>() {

    val homeViewModel:ReplacementViewModel by activityViewModels()
    lateinit var list: List<ReplaceCart>
    @Inject
    lateinit var databaseManager: DatabaseManager
    private var paymentMethod = PaymentMethod.CASH
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getCartFlow().observe(viewLifecycleOwner){
            list = it
        }

        getViewDataBinding().checkout.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                    databaseManager.addReplaceOrder(
                        ReplaceOrder(0,customerName =getViewDataBinding().name.text.toString(),
                        paymentType = paymentMethod.type, contactNo = getViewDataBinding().contactNo.text.toString(),
                        timestamp = System.currentTimeMillis(), items = list, discount = 0.0, type = QueryType.ADD.type)
                    )

                    //showToast("Checkout Successfully")
                    //findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToOrderCompleteFragment())
                databaseManager.clearReplacementCart()
                showSuccessDialog(requireContext(),layoutInflater,getString(R.string.order_success_replace)){
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.homeFragment)
                }.show()

            }
        }
    }






    override fun getLayoutId() = R.layout.fragment_replacement_checkout

    override fun getViewModel() = homeViewModel
}