package com.arifrgilang.presentation.ui.promodetail

import android.os.Bundle
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PromoDetailFragment : BaseBindingFragment<FragmentPromoDetailBinding>() {
    private val args: PromoDetailFragmentArgs by navArgs()
    private val rvAdapter: PromoDetailRvAdapter by inject()
    private val viewModel by viewModel<PromoDetailViewModel>()
    private lateinit var currentList: List<ItemUiModel>
    private lateinit var currentPromo: PromoUiModel
    private var promoName = ""

    override fun contentView(): Int = R.layout.fragment_promo_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModelObservers()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel.getPromo(args.promoId)
    }

    private fun setViewModelObservers() {
        viewModel.isLoading.observe(this, ::showLoading)
        viewModel.isError.observeEvent(this, ::showError)
        viewModel.promoData.observe(this, ::onDataFetched)
    }

    private fun onDataFetched(promoUiModel: PromoUiModel) {
        currentList = promoUiModel.items
        currentPromo = promoUiModel
        logFirebaseViewItemList(promoUiModel.items)
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
                            logFirebaseSelectItem(extra.split(" ")[1])
                            navigateToDetail(extra.split(" ")[0])
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

    private fun logFirebaseViewItemList(list: List<ItemUiModel>) {
        Timber.d("LogFirebaseViewItemList")
        val arrBundle = mutableListOf<Bundle>()

        for(item in list){
            val itemBundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, item.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, item.itemName)
                putString(FirebaseAnalytics.Param.ITEM_CATEGORY, item.itemCategory)
                putString(FirebaseAnalytics.Param.ITEM_VARIANT, item.itemVariant)
                putString(FirebaseAnalytics.Param.ITEM_BRAND, item.itemBrand)
                putDouble(FirebaseAnalytics.Param.PRICE, item.itemPrice!!.toDouble())
            }
            val itemBundleWithIndex = Bundle(itemBundle).apply {
                putLong(FirebaseAnalytics.Param.INDEX, list.indexOf(item).toLong())
            }
            arrBundle.add(itemBundleWithIndex)
        }

        promoName = when (args.promoId) {
            1 -> "Promo Discount 90%"
            2 -> "Promo Free Shipping"
            else -> ""
        }

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST) {
                param(FirebaseAnalytics.Param.ITEM_LIST_ID, args.promoId.toString())
                param(FirebaseAnalytics.Param.ITEM_LIST_NAME, promoName)
                param(FirebaseAnalytics.Param.ITEMS, arrBundle.toTypedArray())
            }
        logFirebaseViewAndSelectPromo(arrBundle)
    }

    private fun logFirebaseViewAndSelectPromo(list: MutableList<Bundle>) {
        Timber.d("LogFirebaseViewAndSelectPromo")
        val promoParams = Bundle().apply {
            putString(FirebaseAnalytics.Param.PROMOTION_ID, currentPromo.id.toString())
            putString(FirebaseAnalytics.Param.PROMOTION_NAME, currentPromo.promotionName)
            putString(FirebaseAnalytics.Param.CREATIVE_NAME, currentPromo.creativeName)
            putString(FirebaseAnalytics.Param.CREATIVE_SLOT, currentPromo.creativeSlot)
            putString(FirebaseAnalytics.Param.LOCATION_ID, currentPromo.locationId)
            putParcelableArray(FirebaseAnalytics.Param.ITEMS, list.toTypedArray())
        }

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promoParams)

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams)
    }

    private fun logFirebaseSelectItem(extra: String) {
        Timber.d("LogFirebaseSelectItem")
        val item = currentList[extra.toInt()]

        val itemBundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, item.id.toString())
            putString(FirebaseAnalytics.Param.ITEM_NAME, item.itemName)
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, item.itemCategory)
            putString(FirebaseAnalytics.Param.ITEM_VARIANT, item.itemVariant)
            putString(FirebaseAnalytics.Param.ITEM_BRAND, item.itemBrand)
            putDouble(FirebaseAnalytics.Param.PRICE, item.itemPrice!!.toDouble())
        }

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_LIST_ID, "Promo ${args.promoId}")
                param(FirebaseAnalytics.Param.ITEM_LIST_NAME, promoName)
                param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemBundle))
            }
    }
}