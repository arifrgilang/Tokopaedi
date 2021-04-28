package com.arifrgilang.presentation.ui.historydetail

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
import com.arifrgilang.presentation.databinding.FragmentHistoryDetailBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.view.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDetailFragment : BaseBindingFragment<FragmentHistoryDetailBinding>() {
    private val args: HistoryDetailFragmentArgs by navArgs()
    private val viewModel by viewModel<HistoryDetailViewModel>()
    private val rvAdapter: HistoryDetailRvAdapter by inject()

    override fun contentView(): Int = R.layout.fragment_history_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModelObservers()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel.getHistoryItemsWithId(args.historyId)
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
        binding.rvHistoryDetail.isVisible = !isLoading
        binding.pbHistoryDetail.isVisible = isLoading
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
        with(binding.rvHistoryDetail) {
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