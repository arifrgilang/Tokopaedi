package com.arifrgilang.presentation.util

import android.app.Activity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


/**
 * Created by arifrgilang on 4/24/2021
 */
object UserManager {
    fun Activity.login() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            Constant.GOOGLE_SIGN_IN
        )
    }

    fun Activity.logout(callback: OnLogout) {
        AuthUI.getInstance()
            .signOut(this)
//            .delete(this)
            .addOnCompleteListener {
                callback.perform()
            }
    }

    fun isUserLoggedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    interface OnLogout{
        fun perform()
    }
}