package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.StocksProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.User
import com.ankurupadhyay.salemanagement.data.UserModel
import com.ankurupadhyay.salemanagement.data.request.LoginRequest
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllProductsBinding
import com.ankurupadhyay.salemanagement.databinding.FragmentLoginBinding
import com.ankurupadhyay.salemanagement.session.SessionManager
import com.ankurupadhyay.salemanagement.utils.Common
import com.ankurupadhyay.salemanagement.worker.ReceiveSyncDataWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment:BaseFragment<FragmentLoginBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by viewModels()
    @Inject
    lateinit var databaseManager: DatabaseManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = WorkManager.getInstance(requireActivity())

        getViewDataBinding().submit.setOnClickListener {
            if (Common.isEmpty(getViewDataBinding().mobile)||Common.isEmpty(getViewDataBinding().password)) {
                if (Common.isEmpty(getViewDataBinding().mobile))
                getViewDataBinding().mobile.error = "Please enter user mobile no"
                if (Common.isEmpty(getViewDataBinding().password))
                getViewDataBinding().password.error = "Please enter password"

            }else{
                showLoading(true)
                homeViewModel.loginUser(LoginRequest(getViewDataBinding().mobile.text.toString(),
                getViewDataBinding().password.text.toString())).observe(viewLifecycleOwner){
                    it.getErrorIfExists().let {
                        Log.d("mylogerror", "onViewCreated: $it")
                    }
                    it.getValueOrNull()?.let {
                        showLoading(false)
                        if (it.status==0)
                            showToast("Please enter correct userid and password")
                        else
                        {
                            if (it.status==1)
                            {
                                val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ReceiveSyncDataWorker>().build()
                                manager.enqueue(oneTimeWorkRequest)
                                it.result.let {
                                    databaseManager.addUser(User(it.id,it.user_id,it.user_name,
                                    it.shop_name,it.shop_place,it.user_status,it.pass,Calendar.getInstance().time))
                                }
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            }

                        }
                    }
                }
            }
        }
    }


    override fun getLayoutId() = R.layout.fragment_login

    override fun getViewModel() = homeViewModel
}