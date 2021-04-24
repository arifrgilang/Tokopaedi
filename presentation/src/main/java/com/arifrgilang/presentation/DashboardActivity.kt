package com.arifrgilang.presentation

import android.content.Intent
import android.os.Bundle
import com.arifrgilang.presentation.databinding.ActivityDashboardBinding
import com.arifrgilang.presentation.login.LoginActivity
import com.arifrgilang.presentation.util.UserManager
import com.arifrgilang.presentation.util.UserManager.logout
import com.arifrgilang.presentation.util.base.BaseBindingActivity
import com.arifrgilang.presentation.util.view.LogoutDialogFragment
import com.google.android.material.chip.Chip

class DashboardActivity : BaseBindingActivity<ActivityDashboardBinding>() {
    override fun contentView(): Int = R.layout.activity_dashboard

    override fun setupData(savedInstanceState: Bundle?) {}

    override fun setupView() {
        if (!UserManager.isUserLoggedIn()) navigateToLogin()
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
        setupChipGroupClothes()
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