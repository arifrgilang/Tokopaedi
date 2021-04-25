package com.arifrgilang.presentation.ui.dashboard

import android.os.Bundle
import androidx.core.view.isVisible
import com.arifrgilang.presentation.ui.MainActivity
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentDashboardBinding
import com.arifrgilang.presentation.model.UserUiModel
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import com.arifrgilang.presentation.util.view.LogoutDialogFragment
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment : BaseBindingFragment<FragmentDashboardBinding>() {
    private val viewModel by viewModel<DashboardViewModel>()
    override fun contentView(): Int = R.layout.fragment_dashboard

    override fun setupData(savedInstanceState: Bundle?) {
        setViewModelObservers()
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
        requireContext().toast("Error occurred")
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
                    (activity as MainActivity).performLogout()
                }
            }
        ).show(
            requireActivity().supportFragmentManager,
            "Logout Dialog Fragment"
        )
    }
}