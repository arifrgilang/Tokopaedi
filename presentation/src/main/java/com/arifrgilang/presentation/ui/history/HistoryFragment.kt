package com.arifrgilang.presentation.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentHistoryBinding
import com.arifrgilang.presentation.model.CheckoutUiModel
import com.arifrgilang.presentation.model.HistoryUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.view.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseBindingFragment<FragmentHistoryBinding>() {
    private val viewModel by viewModel<HistoryViewModel>()
    private val rvAdapter: HistoryRvAdapter by inject()

    override fun contentView(): Int = R.layout.fragment_history

    override fun setupData(savedInstanceState: Bundle?) {
        setViewModelObservers()
        viewModel.getHistoryItems()
    }

    private fun setViewModelObservers() {
        viewModel.historyItems.observe(this, ::onDataFetched)
        viewModel.isLoading.observe(this, ::onLoading)
        viewModel.isError.observe(this, ::onError)
    }

    private fun onDataFetched(items: List<HistoryUiModel>) {
        rvAdapter.clearAndNotify()
        rvAdapter.insertAndNotify(items)
    }

    private fun onError(unit: Event<Unit>) {
        requireContext().toast("Error Occurred")
    }

    private fun onLoading(isLoading: Boolean) {
        binding.rvHistory.isVisible = !isLoading
        binding.pbHistory.isVisible = isLoading
    }


    override fun setupView() {
        initRecyclerView()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerView() {
        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter.apply {
                setOnItemClickListener(
                    object: BaseRecyclerAdapter.AdapterOnClick {
                        override fun onRecyclerItemClicked(extra: String) {
                            navigateToHistoryDetail(extra.toInt())
                        }
                    }
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

    private fun navigateToHistoryDetail(historyId: Int) {
        findNavController().navigate(
            HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment(historyId)
        )
    }

}