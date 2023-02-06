package com.ankurupadhyay.salemanagement

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ankurupadhyay.salemanagement.base.BaseActivity
import com.ankurupadhyay.salemanagement.data.Order
import com.ankurupadhyay.salemanagement.data.ReplaceOrder
import com.ankurupadhyay.salemanagement.data.request.SyncOrderRequest
import com.ankurupadhyay.salemanagement.data.request.SyncReplaceOrderRequest
import com.ankurupadhyay.salemanagement.database.DatabaseManager
import com.ankurupadhyay.salemanagement.session.SessionManager
import com.ankurupadhyay.salemanagement.ui.HomeViewModel
import com.ankurupadhyay.salemanagement.utils.PageConfiguration
import com.ankurupadhyay.salemanagement.worker.ReceiveSyncDataWorker
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navigationController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    val homeViewModel: HomeViewModel by viewModels()

    lateinit var replaceList: List<ReplaceOrder>
    lateinit var userId: String
    lateinit var context:BaseActivity
    @Inject
    lateinit var databaseManager: DatabaseManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar2)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navigationController = navHost.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.splashScreenFragment,R.id.loginFragment,R.id.homeFragment))
        setupActionBarWithNavController(navigationController,appBarConfiguration)
        lifecycleScope.launchWhenResumed {
            navigationController.addOnDestinationChangedListener{_,destination,_->
                onDestinationChange(destination)
            }
        }

        context = this
        homeViewModel.getAllReplaceOrders().observe(this){
            replaceList = it
        }
        homeViewModel.getUserFlow().observe(this){
            userId = it?.id.toString()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()||navigationController.navigateUp()
    }


    fun onDestinationChange(destination:NavDestination){
        val config = PageConfiguration.getConfiguration(destination.id)
        toolBar2.isVisible = config.toolbarVisible
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sync->{
                syncAllData()
            }

            R.id.logout->{
                databaseManager.deleteUser()
                showToast("Logout Successfully")
                navigationController.popBackStack()
                navigationController.navigate(R.id.splashScreenFragment)
            }else->{
            navigationController.popBackStack()
        }
        }


        return true
    }

    private fun syncAllData() {
        showLoading(true)
        val manager = WorkManager.getInstance(this)





        homeViewModel.syncProductVariant().observe(this){
            it.getValueOrNull()?.let {
                if(it.status==1) {
                    showToast("Data sync successfully ")
                    val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ReceiveSyncDataWorker>().build()
                    manager.enqueue(oneTimeWorkRequest)
                    showLoading(false)
                    databaseManager.updateUser(Calendar.getInstance().time)
                } else showToast("Something went wrong")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val list = databaseManager.getAllOrders()
            homeViewModel.syncOrderData(SyncOrderRequest(userId,list)).observe(context){
                it.getValueOrNull()?.let {

                    showToast("Successfully sync data ")

                }
            }
        }

        homeViewModel.syncReplaceOrderData(SyncReplaceOrderRequest(userId,replaceList)).observe(this){
            it?.getValueOrNull()?.let {

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
}