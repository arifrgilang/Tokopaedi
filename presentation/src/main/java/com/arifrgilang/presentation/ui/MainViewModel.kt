package com.arifrgilang.presentation.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI


/**
 * Created by arifrgilang on 4/24/2021
 */
abstract class MainViewModel : ViewModel() {
    abstract fun login()
    abstract fun logout(context: Context, callback: OnLogout)

    interface OnLogout{
        fun onAgree()
    }
}

class MainViewModelImpl: MainViewModel() {
    override fun login() {

    }

    override fun logout(context: Context, callback: OnLogout) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
                callback.onAgree()
            }
    }
}