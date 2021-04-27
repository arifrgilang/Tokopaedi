package com.arifrgilang.presentation.ui.checkout

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentCheckoutBinding
import com.arifrgilang.presentation.model.CheckoutUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment : BaseBindingFragment<FragmentCheckoutBinding>() {
    private val viewModel by viewModel<CheckoutViewModel>()
    private val rvAdapter: CheckoutRvAdapter by inject()

    override fun contentView(): Int = R.layout.fragment_checkout

    override fun setupData(savedInstanceState: Bundle?) {
        setViewModelObservers()
        viewModel.getCheckoutItems()
    }

    private fun setViewModelObservers() {
        viewModel.checkoutItems.observe(this, ::onDataFetched)
        viewModel.isItemCheckoutDeleted.observeEvent(this, ::onItemDeleted)
        viewModel.isItemPaid.observeEvent(this, ::onItemPaid)
        viewModel.isLoading.observe(this, ::onLoading)
        viewModel.isError.observe(this, ::onError)
    }

    private fun onError(unit: Event<Unit>) {
        requireContext().toast("Error Occurred")
    }

    private fun onLoading(isLoading: Boolean) {
        binding.rvCheckout.isVisible = !isLoading
        binding.pbCheckout.isVisible = isLoading
    }

    private fun onItemPaid(isPaid: Boolean) {
        if(isPaid) {
            requireContext().toast("Item successfully paid!")
            findNavController().navigateUp()
        }
    }

    private fun onItemDeleted(isDeleted: Boolean) {
        if(isDeleted) {
            requireContext().toast("Item removed")
            viewModel.getCheckoutItems()
        }
    }

    private fun onDataFetched(items: List<CheckoutUiModel>) {
        rvAdapter.clearAndNotify()
        rvAdapter.insertAndNotify(items)
    }

    override fun setupView() {
        initRecyclerView()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
//        binding
    }

    private fun negativeCallback() = object : BaseRecyclerAdapter.AdapterOnClick {
        override fun onRecyclerItemClicked(extra: String) {
            ConfirmRemoveDialogFragment(
                object : ConfirmRemoveDialogFragment.DialogCallback {
                    override fun isAgree() {
                        viewModel.removeCheckout(extra)
                    }
                }
            ).show(
                requireActivity().supportFragmentManager,
                "Confirm remove checkout"
            )
        }
    }

    private fun positiveCallback() = object: CheckoutRvAdapter.OnPayClicked {
        override fun onAgree(item: CheckoutUiModel) {
            ConfirmPayDialogFragment(
                object : ConfirmPayDialogFragment.DialogCallback {
                    override fun isAgree() {
                        viewModel.payItems(item)
                    }
                }
            ).show(
                requireActivity().supportFragmentManager,
                "Confirm pay checkout"
            )
        }
    }

    private fun detailCallback() = object: BaseRecyclerAdapter.AdapterOnClick {
        override fun onRecyclerItemClicked(extra: String) {
            findNavController().navigate(
                CheckoutFragmentDirections
                    .actionCheckoutFragmentToCheckoutDetailFragment(extra.toInt())
            )
        }
    }

    private fun initRecyclerView() {
        with(binding.rvCheckout) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter.apply {
                setOnItemClickListener(
                    negativeCallback()
                )
                setPositiveCallback(
                    positiveCallback()
                )
                setDetailCallback(
                    detailCallback()
                )
            }
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