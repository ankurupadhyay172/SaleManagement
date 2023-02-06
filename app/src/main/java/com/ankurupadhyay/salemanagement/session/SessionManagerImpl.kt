package com.ankurupadhyay.salemanagement.session

import android.content.Context
import android.content.SharedPreferences
import com.ankurupadhyay.salemanagement.data.User
import com.ankurupadhyay.salemanagement.data.UserModel
import com.ankurupadhyay.salemanagement.data.response.LoginResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(@ApplicationContext context:Context):SessionManager {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    companion object{
        const val PREF_NAME = "user_session"
        const val USER_DATA = "user_data"
    }

    override fun saveUser(user: User?) {
        user?.let {
            val editor = prefs.edit()
            editor.putString(USER_DATA, Gson().toJson(user))
            editor.apply()
        }
    }

    override fun getUser(): User? {
        return if (prefs.getString(USER_DATA,null)!=null)
            Gson().fromJson(prefs.getString(USER_DATA,null), User::class.java)
        else
            null
    }

    override fun deleteUser() {
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

}