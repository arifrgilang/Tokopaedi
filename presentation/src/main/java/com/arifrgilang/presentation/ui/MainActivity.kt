package com.arifrgilang.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ActivityMainBinding
import com.arifrgilang.presentation.ui.dashboard.DashboardFragmentDirections
import com.arifrgilang.presentation.ui.login.LoginFragmentDirections
import com.arifrgilang.presentation.util.Constant.GOOGLE_SIGN_IN
import com.arifrgilang.presentation.util.base.BaseBindingActivity
import com.arifrgilang.presentation.util.view.toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_OpakuShop)
        super.onCreate(savedInstanceState)
    }

    override fun contentView(): Int = R.layout.activity_main

    override fun setupData(savedInstanceState: Bundle?) {
        mAuthListener = getAuthStateListener()
        viewModel.populateDatabase(this)
    }

    override fun setupView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private fun getAuthStateListener() = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if(user != null){
            navigateToDashboard()
        }
    }

    fun performLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            GOOGLE_SIGN_IN
        )
    }

    fun performLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                navigateToLogin()
            }
    }

    private fun navigateToDashboard() {
        val currentDestId = navHostFragment.findNavController().currentDestination?.id
        if(currentDestId == R.id.loginFragment) {
            navHostFragment.findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
            )
            logFirebaseLogin()
            toast("Logged in successfully")
        }
    }

    private fun navigateToLogin() {
        val currentDestId = navHostFragment.findNavController().currentDestination?.id
        if(currentDestId == R.id.dashboardFragment) {
            navHostFragment.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToLoginFragment()
            )
            toast("Logged out successfully")
        }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener)
    }

    override fun onPause() {
        super.onPause()
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                toast("Login failed")
            }
        }
    }

    private fun logFirebaseLogin() {
        FirebaseAnalytics.getInstance(this)
            .logEvent(
                FirebaseAnalytics.Event.LOGIN, Bundle().apply {
                    putString(FirebaseAnalytics.Param.METHOD, "Google")
                }
            )
    }
}