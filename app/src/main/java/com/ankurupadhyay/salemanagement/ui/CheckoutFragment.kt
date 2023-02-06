package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.MyCart
import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentCheckoutBinding
import com.ankurupadhyay.salemanagement.utils.PaymentMethod
import com.ankurupadhyay.salemanagement.utils.QueryType
import com.ankurupadhyay.salemanagement.utils.setPrice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CheckoutFragment:BaseFragment<FragmentCheckoutBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by activityViewModels()
    lateinit var list: List<MyCart>
    @Inject
    lateinit var databaseManager: DatabaseManager
    private var paymentMethod = PaymentMethod.CASH
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showTotal()
        setPaymentMethod()
        homeViewModel.getCartFlow().observe(viewLifecycleOwner){
            list = it
        }

        getViewDataBinding().checkout.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                    databaseManager.addOrder(Order(0,customerName =getViewDataBinding().name.text.toString(),
                        paymentType = paymentMethod.type, contactNo = getViewDataBinding().contactNo.text.toString(),
                        timestamp = System.currentTimeMillis(), items = list, discount = homeViewModel.discount, type = QueryType.ADD.type))
                    for (i in list){
                        val qua = i.variants.quantity-i.variants.selectedQuan
                        databaseManager.updateProductStatus(i.variants.pid,QueryType.UPDATE.type)
                        databaseManager.updateVariantQuan(i.variants.id,qua)
                    }
                    showToast("Checkout Successfully")
                    findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToOrderCompleteFragment())
            }
        }
    }

    private fun setPaymentMethod() {
        getViewDataBinding().liCash.setOnClickListener {
            paymentMethod = PaymentMethod.CASH
            paymentImage(paymentMethod)
            Log.d("myLogData",""+paymentMethod.type)
        }
        getViewDataBinding().liOnline.setOnClickListener {
            paymentMethod = PaymentMethod.ONLINE
            paymentImage(paymentMethod)
            Log.d("myLogData2",""+paymentMethod.type)
        }
    }

    private fun paymentImage(paymentMethod: PaymentMethod)
    {
        when(paymentMethod)
        {
            PaymentMethod.ONLINE->{
                getViewDataBinding().imgOnline.setImageResource(R.drawable.circle_green)
                getViewDataBinding().imgCash.setImageResource(R.drawable.circle_radio)

            }
            PaymentMethod.CASH->{
                getViewDataBinding().imgCash.setImageResource(R.drawable.circle_green)
                getViewDataBinding().imgOnline.setImageResource(R.drawable.circle_radio)
            }
        }
    }

    private fun showTotal() {
        getViewDataBinding().totalPrice.setPrice(homeViewModel.cartTotal)
        getViewDataBinding().discount.setPrice(homeViewModel.discount)
        getViewDataBinding().grandTotal.setPrice(homeViewModel.grandTotal)
    }


    override fun getLayoutId() = R.layout.fragment_checkout

    override fun getViewModel() = homeViewModel
}