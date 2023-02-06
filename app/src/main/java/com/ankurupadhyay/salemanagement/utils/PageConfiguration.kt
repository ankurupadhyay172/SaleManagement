package com.ankurupadhyay.salemanagement.utils

import androidx.annotation.IdRes
import com.ankurupadhyay.salemanagement.R

enum class PageConfiguration(
    val id: Int,
    val toolbarVisible: Boolean=true,
    val bottomNavigationVisible: Boolean=false){

    SPLASH(
        R.id.splashScreenFragment,
        false
    ),HOME(
        R.id.homeFragment,
        true
    ),LOGIN(
        R.id.loginFragment,
        false
    ),OTHER(0);

    companion object{
            fun getConfiguration(@IdRes id: Int):PageConfiguration{
                return values().firstOrNull{it.id==id}?:OTHER
            }
    }
}