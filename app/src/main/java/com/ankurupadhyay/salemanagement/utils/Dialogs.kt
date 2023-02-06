package com.ankurupadhyay.salemanagement.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.SpannableString
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.databinding.ErrorLayoutBinding
import com.ankurupadhyay.salemanagement.databinding.WarningLayoutBinding


fun showErrorDialog(context: Context,
                      layoutInflater: LayoutInflater,
                      msg : String):Dialog{
    val binding = DataBindingUtil.inflate<ErrorLayoutBinding>(layoutInflater,
        R.layout.error_layout,null,true)

    return Dialog(context,R.style.DialogFragmentThemeCompact).apply {
        setContentView(binding.root)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        binding.apply {
            val str = msg
            tvErrorMessage.text = SpannableString(str)
        }
        binding.btnYes.setOnClickListener {
            dismiss()
        }
    }
}


fun showSuccessDialog(context: Context,
                    layoutInflater: LayoutInflater,
                    msg : String,
                    actionOk :()->Unit):Dialog{
    val binding = DataBindingUtil.inflate<ErrorLayoutBinding>(layoutInflater,
        R.layout.error_layout,null,true)

    return Dialog(context,R.style.DialogFragmentThemeCompact).apply {
        setContentView(binding.root)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        binding.tvWelcome.text = "Success"

        binding.apply {
            val str = msg
            tvErrorMessage.text = SpannableString(str)
        }
        binding.btnYes.setOnClickListener {
            actionOk.invoke()
            dismiss()
        }
    }
}


fun showDeleteWarningDialog(context: Context,
                      layoutInflater: LayoutInflater,
                      msg : String,
                      actionDelete:()->Unit):Dialog{
    val binding = DataBindingUtil.inflate<WarningLayoutBinding>(layoutInflater,
        R.layout.warning_layout,null,true)

    return Dialog(context,R.style.DialogFragmentThemeCompact).apply {
        setContentView(binding.root)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        binding.apply {
            val str = msg
            tvErrorMessage.text = SpannableString(str)
        }
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            dismiss()
         actionDelete.invoke()
        }
    }
}