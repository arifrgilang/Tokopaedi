package com.arifrgilang.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.arifrgilang.presentation.dashboard.DashboardActivity
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ActivityLoginBinding
import com.arifrgilang.presentation.util.Constant.GOOGLE_SIGN_IN
import com.arifrgilang.presentation.util.UserManager.isUserLoggedIn
import com.arifrgilang.presentation.util.UserManager.login
import com.arifrgilang.presentation.util.base.BaseBindingActivity
import com.arifrgilang.presentation.util.toast
import timber.log.Timber

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_OpakuShop)
        super.onCreate(savedInstanceState)
    }

    override fun contentView(): Int = R.layout.activity_login

    override fun setupData(savedInstanceState: Bundle?) {}

    override fun setupView() {
        if(isUserLoggedIn()) {
            Timber.d("User is logged in")
            navigateToDashboard()
        }
        binding.btnLogin.setOnClickListener() {
            login()
        }
    }

    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                navigateToDashboard()
            } else {
                toast("Login failed")
            }
        }
    }
}