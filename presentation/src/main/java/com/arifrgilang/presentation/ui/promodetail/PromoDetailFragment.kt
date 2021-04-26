package com.arifrgilang.presentation.ui.promodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentPromoDetailBinding
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.model.PromoUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PromoDetailFragment : BaseBindingFragment<FragmentPromoDetailBinding>() {
    private val args: PromoDetailFragmentArgs by navArgs()
    private val rvAdapter: PromoDetailRvAdapter by inject()
    private val viewModel by viewModel<PromoDetailViewModel>()

    override fun contentView(): Int = R.layout.fragment_promo_detail

    override fun setupData(savedInstanceState: Bundle?) {
        setViewModelObservers()
        viewModel.getPromo(args.promoId)
    }

    private fun setViewModelObservers() {
        viewModel.isLoading.observe(this, ::showLoading)
        viewModel.isError.observeEvent(this, ::showError)
        viewModel.promoData.observe(this, ::onDataFetched)
    }

    private fun onDataFetched(promoUiModel: PromoUiModel) {
        rvAdapter.clearAndNotify()
        rvAdapter.insertAndNotify(promoUiModel.items)
    }

    private fun showError(unit: Unit) {
        requireContext().toast("Error Occurred")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvPromo.isVisible = !isLoading
        binding.pbPromo.isVisible = isLoading
    }

    override fun setupView() {
        initRecyclerView()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        rvAdapter.clearAndNotify()
    }

    private fun initRecyclerView() {
        with(binding.rvPromo) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = rvAdapter.apply {
                setOnItemClickListener(
                    object: BaseRecyclerAdapter.AdapterOnClick {
                        override fun onRecyclerItemClicked(extra: String) {
                            navigateToDetail(extra)
                        }
                    }
                )
            }
            addItemDecoration(
                CustomRvMargin(
                    requireContext(),
                    16,
                    CustomRvMargin.GRID_2
                )
            )
        }
    }

    private fun navigateToDetail(itemId: String) {
        findNavController()
            .navigate(
                PromoDetailFragmentDirections.actionPromoDetailFragmentToItemDetailFragment(
                    itemId.toInt()
                )
            )
    }
}