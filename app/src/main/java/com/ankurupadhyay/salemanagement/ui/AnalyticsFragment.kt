package com.ankurupadhyay.salemanagement.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.adapters.OrdersAdapter
import com.ankurupadhyay.salemanagement.base.BaseFragment
import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.databinding.FragmentAnalyticsBinding
import com.ankurupadhyay.salemanagement.utils.AnalyticsType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnalyticsFragment : BaseFragment<FragmentAnalyticsBinding, HomeViewModel>() {

    val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var adapter: OrdersAdapter
    @Inject
    lateinit var databaseManager: DatabaseManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
        setCurrentDate()
        setSelectedAnalytics()

    }

    private fun setSelectedAnalytics() {
        getViewDataBinding().dayByDay.setOnClickListener {
            getSelectedAnalytics(AnalyticsType.DayByDay.type)
        }
        getViewDataBinding().monthByMonth.setOnClickListener {
            getSelectedAnalytics(AnalyticsType.MonthByMonth.type)
        }
        getViewDataBinding().yearByYear.setOnClickListener {
            getSelectedAnalytics(AnalyticsType.YearByYear.type)
        }
        homeViewModel.filterValue.observe(viewLifecycleOwner) {
            when (it) {
                AnalyticsType.DayByDay.type -> {
                    homeViewModel.getDateWiseFlow(homeViewModel.analyticsType.value!!)
                        .observe(viewLifecycleOwner) {
                            setOrderData(it)
                        }
                }
                AnalyticsType.MonthByMonth.type -> {
                    homeViewModel.getMonthWiseOrder(homeViewModel.analyticsType.value!!)
                        .observe(viewLifecycleOwner) {
                            setOrderData(it)
                        }
                }
                AnalyticsType.YearByYear.type -> {
                    homeViewModel.getYearWiseOrder(homeViewModel.analyticsType.value!!)
                        .observe(viewLifecycleOwner) {
                            setOrderData(it)
                        }
                }
            }
        }

        homeViewModel.analyticsType.observe(viewLifecycleOwner) {
            getViewDataBinding().date.text = homeViewModel.setFilterData(it)
            when(homeViewModel.filterValue.value)
            {
                AnalyticsType.DayByDay.type->{
                    homeViewModel.getDateWiseFlow(it).observe(viewLifecycleOwner) {
                        setOrderData(it)
                    }
                }
                AnalyticsType.MonthByMonth.type->{
                    homeViewModel.getMonthWiseOrder(it).observe(viewLifecycleOwner) {
                        setOrderData(it)
                    }
                }
                AnalyticsType.YearByYear.type->{
                    homeViewModel.getYearWiseOrder(it).observe(viewLifecycleOwner) {
                        setOrderData(it)
                    }
                }
            }
        }
    }

    private fun setCurrentDate() {
        getViewDataBinding().ivBack.setOnClickListener {
            homeViewModel.decrementCounter()
        }
        getViewDataBinding().ivForward.setOnClickListener {
            homeViewModel.incrementCounter()
        }
    }


    fun setOrderData(order: List<Order>) {
        adapter.submitList(order)
        var online = 0.0
        var offline = 0.0
        var gTotal = 0.0
        var profit = 0.0
        for (i in order) {
            if (i.paymentType == 1) {
                offline += i.items?.sumOf { it.variants.sellingPrice * it.variants.selectedQuan  }!!
                offline -=i.discount
            } else {
                online += i.items?.sumOf { it.variants.sellingPrice * it.variants.selectedQuan  }!!
                online -=i.discount

            }
            gTotal += i.items.sumOf { (it.variants.sellingPrice * it.variants.selectedQuan)  }
            gTotal -= i.discount

            profit += i.items.sumOf { (it.variants.sellingPrice - it.variants.acPrice)*it.variants.selectedQuan }
            profit -= i.discount
            Log.d("mylogdata", "setOrderData: " + profit)
        }

        getViewDataBinding().tvOnline.text = "Rec. Cash = ₹ $offline"
        getViewDataBinding().tvOffline.text = "Rec. Online = ₹ $online"
        getViewDataBinding().tvTodaySale.text = "$gTotal ₹"
        getViewDataBinding().txtAnalytics.text = "Total Profit = $profit"

        getViewDataBinding().isEmpty = order.isEmpty()
    }

    fun getSelectedAnalytics(type: Int) {
        getViewDataBinding().dayByDay.setBackgroundResource(R.drawable.item_circular_background)
        getViewDataBinding().monthByMonth.setBackgroundResource(R.drawable.item_circular_background)
        getViewDataBinding().yearByYear.setBackgroundResource(R.drawable.item_circular_background)
        getViewDataBinding().dayByDay.setTextColor(R.color.black)
        getViewDataBinding().monthByMonth.setTextColor(R.color.black)
        getViewDataBinding().yearByYear.setTextColor(R.color.black)

        when (type) {
            AnalyticsType.DayByDay.type -> {
                homeViewModel.filterValue.value = AnalyticsType.DayByDay.type
                getViewDataBinding().dayByDay.setBackgroundResource(R.drawable.item_active_circular_background)
                getViewDataBinding().dayByDay.setTextColor(R.color.green)
                getViewDataBinding().date.text =
                    homeViewModel.setFilterData(homeViewModel.analyticsType.value!!)

            }
            AnalyticsType.MonthByMonth.type -> {
                homeViewModel.filterValue.value = AnalyticsType.MonthByMonth.type
                getViewDataBinding().monthByMonth.setBackgroundResource(R.drawable.item_active_circular_background)
                getViewDataBinding().monthByMonth.setTextColor(R.color.green)
                getViewDataBinding().date.text =
                    homeViewModel.setFilterData(homeViewModel.analyticsType.value!!)

            }
            AnalyticsType.YearByYear.type -> {
                homeViewModel.filterValue.value = AnalyticsType.YearByYear.type
                getViewDataBinding().yearByYear.setBackgroundResource(R.drawable.item_active_circular_background)
                getViewDataBinding().yearByYear.setTextColor(R.color.green)
                getViewDataBinding().date.text =
                    homeViewModel.setFilterData(homeViewModel.analyticsType.value!!)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_analytics

    override fun getViewModel() = homeViewModel
}