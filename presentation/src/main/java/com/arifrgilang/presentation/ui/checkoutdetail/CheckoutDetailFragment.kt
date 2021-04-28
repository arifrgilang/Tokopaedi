package com.arifrgilang.presentation.ui.checkoutdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentCheckoutBinding
import com.arifrgilang.presentation.databinding.FragmentCheckoutDetailBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.view.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutDetailFragment : BaseBindingFragment<FragmentCheckoutDetailBinding>() {
    private val args: CheckoutDetailFragmentArgs by navArgs()
    private val viewModel by viewModel<CheckoutDetailViewModel>()
    private val rvAdapter: CheckoutDetailRvAdapter by inject()

    override fun contentView(): Int = R.layout.fragment_checkout_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModelObservers()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel.getCheckoutItemsWithId(args.checkoutId)
    }

    private fun setViewModelObservers() {
        viewModel.cartItems.observe(this, ::onDataFetched)
        viewModel.isLoading.observe(this, ::onLoading)
        viewModel.isError.observe(this, ::onError)
    }

    private fun onError(unit: Event<Unit>) {
        requireContext().toast("Error Occurred")
    }

    private fun onLoading(isLoading: Boolean) {
        binding.rvCheckoutDetail.isVisible = !isLoading
        binding.pbCheckoutDetail.isVisible = isLoading
    }

    private fun onDataFetched(items: List<CartUiModel>) {
        rvAdapter.clearAndNotify()
        rvAdapter.insertAndNotify(items)
    }

    override fun setupView() {
        initRecyclerView()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerView() {
        with(binding.rvCheckoutDetail) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter.apply {
                addItemDecoration(
                    CustomRvMargin(
                        requireContext(),
                        16,
                        CustomRvMargin.LINEAR_VERTICAL
                    )
                )
            }
        }
    }
}