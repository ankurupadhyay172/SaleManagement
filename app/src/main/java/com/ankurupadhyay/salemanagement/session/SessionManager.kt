package com.ankurupadhyay.salemanagement.session

import com.ankurupadhyay.salemanagement.data.User
import com.ankurupadhyay.salemanagement.data.UserModel
import com.ankurupadhyay.salemanagement.data.response.LoginResponse

interface SessionManager {

    fun saveUser(user:User?)
    fun getUser():User?
    fun deleteUser()
}