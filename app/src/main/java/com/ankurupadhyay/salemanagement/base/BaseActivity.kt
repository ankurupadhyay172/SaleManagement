package com.ankurupadhyay.salemanagement.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ankurupadhyay.salemanagement.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity: AppCompatActivity() {
    private var progressDialog : ProgressDialog? = null

    fun showMessage(stringRes:Int)
    {
        val snackbar  = Snackbar.make(findViewById(R.id.container),stringRes, Snackbar.LENGTH_LONG)
        snackbar.anchorView = findViewById(R.id.container)
        snackbar.show()
    }

    fun showToast(s:String?)
    {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show()
    }

    fun showLoading(visible:Boolean){
        if (visible){
            progressDialog = showLoadingDialog(this)
        }else{
            hideLoading()
        }
    }

    fun hideLoading() {
        progressDialog?.cancel()
    }

    private fun showLoadingDialog(context: Context): ProgressDialog? {

        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window!=null){
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.spin_view)
        progressDialog.isIndeterminate = true
        //  progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }
}