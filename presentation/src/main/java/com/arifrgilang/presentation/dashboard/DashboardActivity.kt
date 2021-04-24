package com.arifrgilang.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ActivityDashboardBinding
import com.arifrgilang.presentation.login.LoginActivity
import com.arifrgilang.presentation.model.UserUiModel
import com.arifrgilang.presentation.util.UserManager
import com.arifrgilang.presentation.util.UserManager.logout
import com.arifrgilang.presentation.util.base.BaseBindingActivity
import com.arifrgilang.presentation.util.toast
import com.arifrgilang.presentation.util.view.LogoutDialogFragment
import com.arifrgilang.presentation.util.view.observeEvent
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DashboardActivity : BaseBindingActivity<ActivityDashboardBinding>() {
    private val viewModel by viewModel<DashboardViewModel>()
    override fun contentView(): Int = R.layout.activity_dashboard

    override fun setupData(savedInstanceState: Bundle?) {
        setViewModelObservers()
        if (!UserManager.isUserLoggedIn()) navigateToLogin()
        viewModel.getUserData()
    }

    private fun setViewModelObservers() {
        viewModel.userData.observe(this, ::bindUserData)
        viewModel.isLoadingProfile.observe(this, ::showLoadingProfile)
        viewModel.isError.observeEvent(this, ::showError)
    }

    private fun bindUserData(userData: UserUiModel) {
        binding.user = userData
    }

    private fun showLoadingProfile(isLoading: Boolean) {
        binding.clUser.isVisible = !isLoading
        binding.pbUser.isVisible = isLoading
    }

    private fun showError(unit: Unit) {
        toast("Error occurred")
    }

    override fun setupView() {
        setupChipGroupClothes()
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun setupChipGroupClothes() {
        val clothesList = mutableListOf("Tops & Tees", "Bottoms & Leggings", "Pajamas & Socks", "Dresses & Jumpsuits")
        for (item in clothesList) {
            val chip = (layoutInflater.inflate(
                R.layout.item_selectable_chip,
                binding.cgClothes,
                false) as Chip).apply {
                    text = item
            }
            binding.cgClothes.addView(chip)
        }
        binding.cgClothes.getChildAt(0).performClick()
    }

    private fun showLogoutDialog() {
        LogoutDialogFragment(
            object : LogoutDialogFragment.DialogCallback {
                override fun isAgree() {
                    performLogout()
                }
            }
        ).show(supportFragmentManager, "Logout Dialog Fragment")
    }

    private fun performLogout() {
        logout(object : UserManager.OnLogout {
            override fun perform() {
                navigateToLogin()
            }
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}