package com.arifrgilang.presentation.ui.dashboard

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifrgilang.presentation.ui.MainActivity
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentDashboardBinding
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.model.UserUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class DashboardFragment : BaseBindingFragment<FragmentDashboardBinding>() {
    private val rvAdapter: DashboardRvAdapter by inject()
    private val viewModel by viewModel<DashboardViewModel>()
    private val clothesList = mutableListOf("Tops & Tees", "Bottoms & Leggings", "Pajamas & Socks", "Dresses & Jumpsuits")
    private var currentCategory = "Tops & Tees"
    private lateinit var currentList: List<ItemUiModel>

    override fun contentView(): Int = R.layout.fragment_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModelObservers()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel.getUserData()
    }

    private fun setViewModelObservers() {
        viewModel.userData.observe(this, ::bindUserData)
        viewModel.isLoadingProfile.observe(this, ::showLoadingProfile)
        viewModel.isError.observeEvent(this, ::showError)
        viewModel.clothesData.observe(this, ::onDataFetched)
        viewModel.isLoadingClothes.observe(this, ::showLoadingClothes)

        viewModel.cartCount.observe(this, ::onCartCountFetched)
        viewModel.checkoutCount.observe(this, ::onCheckoutCountFetched)
        viewModel.historyCount.observe(this, ::onHistoryCountFetched)
    }

    private fun onHistoryCountFetched(count: Int) {
        binding.historyCount = count
    }

    private fun onCheckoutCountFetched(count: Int) {
        binding.checkoutCount = count
    }

    private fun onCartCountFetched(count: Int) {
        binding.cartCount = count
    }

    private fun onDataFetched(list: List<ItemUiModel>) {
        currentList = list
        logFirebaseViewItemList(currentList)
        rvAdapter.clearAndNotify()
        rvAdapter.insertAndNotify(list)

    }

    private fun bindUserData(userData: UserUiModel) {
        binding.user = userData
        viewModel.getCartWithEmail(userData.email)
        viewModel.getCheckoutWithEmail(userData.email)
        viewModel.getHistoryWithEmail(userData.email)
    }

    private fun showLoadingProfile(isLoading: Boolean) {
        binding.clUser.isVisible = !isLoading
        binding.pbUser.isVisible = isLoading
    }

    private fun showLoadingClothes(isLoading: Boolean) {
//        binding.rvClothes.isInvisible = isLoading
//        binding.pbUser.isInvisible = !isLoading
    }

    private fun showError(unit: Unit) {
        requireContext().toast("Error occurred")
    }

    override fun setupView() {
        initRecyclerView()
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
        binding.ivPromo1.setOnClickListener {
            navigateToPromo(1)
        }
        binding.ivPromo2.setOnClickListener {
            navigateToPromo(2)
        }
        binding.ivCart.setOnClickListener {
            navigateToCart()
        }
        binding.ivCheckout.setOnClickListener {
            navigateToCheckout()
        }
        binding.ivHistory.setOnClickListener {
            navigateToHistory()
        }
        binding.cgClothes.setOnCheckedChangeListener { _, checkedId ->
            /*
             * - The index need to be modded by statusList.size because CardGroup is generating
             *   unique itemId (n) even after onDestroy()
             * - The minus 1 is because array starts from zero index
             */

            val index = (checkedId%clothesList.size)-1
            val filteredIndex = if(index<0) clothesList.size-1 else index
            Timber.d("Item Clicked $filteredIndex")
            clothesList[filteredIndex].let { category ->
                currentCategory = category
                viewModel.getClothesWithCategory(currentCategory)
            }
        }
        setupChipGroupClothes()
    }

    private fun initRecyclerView() {
        with(binding.rvClothes) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
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
                    CustomRvMargin.LINEAR_HORIZONTAL
                )
            )
        }
    }

    private fun navigateToHistory() {
        findNavController()
            .navigate(
                DashboardFragmentDirections.actionDashboardFragmentToHistoryFragment()
            )
    }

    private fun navigateToCheckout() {
        findNavController()
            .navigate(
                DashboardFragmentDirections.actionDashboardFragmentToCheckoutFragment()
            )
    }

    private fun navigateToCart() {
        findNavController()
            .navigate(
                DashboardFragmentDirections.actionDashboardFragmentToCartFragment()
            )
    }

    private fun navigateToPromo(promoId: Int) {
        findNavController()
            .navigate(
                DashboardFragmentDirections.actionDashboardFragmentToPromoDetailFragment(
                    promoId
                )
            )
    }

    private fun navigateToDetail(itemId: String) {
        findNavController()
            .navigate(
                DashboardFragmentDirections.actionDashboardFragmentToItemDetailFragment(
                    itemId.toInt()
                )
            )
    }

    private fun setupChipGroupClothes() {
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

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST) {
                param(FirebaseAnalytics.Param.ITEM_LIST_ID, clothesList.indexOf(currentCategory).toString())
                param(FirebaseAnalytics.Param.ITEM_LIST_NAME, currentCategory)
                param(FirebaseAnalytics.Param.ITEMS, arrBundle.toTypedArray())
            }
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
                param(FirebaseAnalytics.Param.ITEM_LIST_ID, clothesList.indexOf(currentCategory).toString())
                param(FirebaseAnalytics.Param.ITEM_LIST_NAME, currentCategory)
                param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemBundle))
            }
    }
}