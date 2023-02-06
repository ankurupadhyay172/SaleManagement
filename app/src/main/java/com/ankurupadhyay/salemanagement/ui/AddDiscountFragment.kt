package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentDiscountBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddDiscountFragment : BaseFragment<FragmentDiscountBinding, HomeViewModel>() {

    val homeViewModel: HomeViewModel by activityViewModels()

    @Inject
    lateinit var databaseManager: DatabaseManager

    private val navArgs: AddDiscountFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewDataBinding().discount.setText(navArgs.discount.toString())
        getViewDataBinding().submit.setOnClickListener {
            homeViewModel.discount = getViewDataBinding().discount.text.toString().toDouble()
            findNavController().popBackStack()
            showToast("Discount added successfully")
        }

    }


    override fun getLayoutId() = R.layout.fragment_discount

    override fun getViewModel() = homeViewModel
}