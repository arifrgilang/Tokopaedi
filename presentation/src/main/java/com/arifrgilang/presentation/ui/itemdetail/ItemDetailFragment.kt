package com.arifrgilang.presentation.ui.itemdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentItemDetailBinding
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment : BaseBindingFragment<FragmentItemDetailBinding>() {
    private val args: ItemDetailFragmentArgs by navArgs()
    private val viewModel by viewModel<ItemDetailViewModel>()
    private lateinit var item: ItemUiModel

    override fun contentView(): Int = R.layout.fragment_item_detail

    override fun setupData(savedInstanceState: Bundle?) {
        setViewModelObservers()
        viewModel.getItemDetail(args.itemId)
    }

    private fun setViewModelObservers() {
        viewModel.isLoading.observe(this, ::showLoading)
        viewModel.isError.observeEvent(this, ::showError)
        viewModel.itemData.observe(this, ::onDataFetched)
        viewModel.isAdded.observeEvent(this, ::onAdded)
        viewModel.isLoadingButton.observe(this, ::onLoadingButton)
    }

    private fun onLoadingButton(isLoading: Boolean) {
        binding.btnAddToCart.isVisible = !isLoading
        binding.pbAddToCart.isVisible = isLoading
    }

    private fun onAdded(isAdded: Boolean) {
        if(isAdded){
            requireContext().toast("Item added to cart")
            findNavController().navigateUp()
        }
    }

    private fun onDataFetched(data: ItemUiModel) {
        binding.item = data
        item = data
    }

    private fun showError(unit: Unit) {
        requireContext().toast("Error Occurred")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.llItemDetail.isVisible = !isLoading
        binding.pbItemDetail.isVisible = isLoading
    }

    override fun setupView() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart(item)
        }
    }
}