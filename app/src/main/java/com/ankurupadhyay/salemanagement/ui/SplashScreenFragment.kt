package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.StocksProductsAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.request.LoginRequest
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAllProductsBinding
import com.ankurupadhyay.salemanagement.databinding.FragmentLoginBinding
import com.ankurupadhyay.salemanagement.databinding.FragmentSplashBinding
import com.ankurupadhyay.salemanagement.utils.Common
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment:BaseFragment<FragmentSplashBinding,HomeViewModel>() {

    val homeViewModel:HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            navigationToMain()

        },2000)

    }

    private fun navigationToMain() {
        homeViewModel.getUser().observe(viewLifecycleOwner){
            if(it==null){
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
            }else{
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
            }
        }

    }


    override fun getLayoutId() = R.layout.fragment_splash

    override fun getViewModel() = homeViewModel
}