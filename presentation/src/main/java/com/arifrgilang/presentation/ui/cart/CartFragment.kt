package com.arifrgilang.presentation.ui.cart

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentCartBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class CartFragment : BaseBindingFragment<FragmentCartBinding>() {
    private val viewModel by viewModel<CartViewModel>()
    private val rvAdapter: CartRvAdapter by inject()
    private lateinit var cartItems: List<CartUiModel>

    override fun contentView(): Int = R.layout.fragment_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModelObservers()
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel.getCartItems()
    }

    private fun setViewModelObservers() {
        viewModel.cartItems.observe(this, ::onDataFetched)
        viewModel.isCheckoutClicked.observeEvent(this, ::onCheckoutClicked)
        viewModel.isLoading.observe(this, ::onLoading)
        viewModel.isLoadingButton.observe(this, ::onLoadingButton)
        viewModel.isError.observe(this, ::onError)
        viewModel.isItemCartDeleted.observeEvent(this, ::onItemCartDeleted)
    }

    private fun onItemCartDeleted(isDeleted: Boolean) {
        if(isDeleted){
            requireContext().toast("Item removed from cart")
            viewModel.getCartItems()
        }
    }

    private fun onError(unit: Event<Unit>) {
        requireContext().toast("Error Occurred")
    }

    private fun onLoadingButton(isLoadingButton: Boolean) {
        binding.btnCheckout.isVisible = !isLoadingButton
        binding.pbBtnCheckout.isVisible = isLoadingButton
    }

    private fun onLoading(isLoading: Boolean) {
        binding.rvCart.isVisible = !isLoading
        binding.pbCheckout.isVisible = isLoading
    }

    private fun onCheckoutClicked(isCheckoutClicked: Boolean) {
        if(isCheckoutClicked){
            requireContext().toast("Checkout success!")
            findNavController().navigateUp()
        }
    }

    private fun onDataFetched(items: List<CartUiModel>) {
        binding.totalPrice = countPrice(items)
        binding.btnCheckout.isEnabled = items.isNotEmpty()
        cartItems = items
        if(items.isNotEmpty()){
            logFirebaseViewCart(items, countPrice(items))
        }
        rvAdapter.clearAndNotify()
        rvAdapter.insertAndNotify(items)
    }

    private fun countPrice(items: List<CartUiModel>): Int {
        return if(items.isNotEmpty()){
            var result = 0
            for (item in items){
                result += item.itemPrice!!.toInt()
            }
            result
        } else {
            0
        }
    }

    override fun setupView() {
        initRecyclerView()
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnCheckout.setOnClickListener {
            showConfirmCheckout()
        }
    }

    private fun showConfirmCheckout() {
        ConfirmCheckoutDialogFragment(
            object : ConfirmCheckoutDialogFragment.DialogCallback {
                override fun isAgree() {
                    logFirebaseCheckout()
                    viewModel.checkoutItems(cartItems)
                }
            }
        ).show(
            requireActivity().supportFragmentManager,
            "Confirm Checkout Dialog Fragment"
        )
    }

    private fun initRecyclerView() {
        with(binding.rvCart) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter.apply {
                setOnItemClickListener(
                    object : BaseRecyclerAdapter.AdapterOnClick {
                        override fun onRecyclerItemClicked(extra: String) {
                            logFirebaseRemoveFromCart(extra.split(" ")[1])
                            viewModel.deleteItem(extra.split(" ")[0])
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

    private fun logFirebaseCheckout() {
        Timber.d("LogFirebaseCheckout")
        val arrBundle = mutableListOf<Bundle>()

        for(item in cartItems){
            val itemBundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, item.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, item.itemName)
                putString(FirebaseAnalytics.Param.ITEM_CATEGORY, item.itemCategory)
                putString(FirebaseAnalytics.Param.ITEM_VARIANT, item.itemVariant)
                putString(FirebaseAnalytics.Param.ITEM_BRAND, item.itemBrand)
                putDouble(FirebaseAnalytics.Param.PRICE, item.itemPrice!!.toDouble())
            }
            val itemBundleWithQuantity = Bundle(itemBundle).apply {
                putLong(FirebaseAnalytics.Param.QUANTITY, 1)
            }
            arrBundle.add(itemBundleWithQuantity)
        }

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT) {
                param(FirebaseAnalytics.Param.CURRENCY, "USD")
                param(FirebaseAnalytics.Param.VALUE, countPrice(cartItems).toDouble())
                param(FirebaseAnalytics.Param.COUPON, "NO_COUPON")
                param(FirebaseAnalytics.Param.ITEMS, arrBundle.toTypedArray())
            }
    }

    private fun logFirebaseRemoveFromCart(itemPosition: String) {
        Timber.d("LogFirebaseRemoveFromCart")
        val item = cartItems[itemPosition.toInt()]

        val itemBundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, item.id.toString())
            putString(FirebaseAnalytics.Param.ITEM_NAME, item.itemName)
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, item.itemCategory)
            putString(FirebaseAnalytics.Param.ITEM_VARIANT, item.itemVariant)
            putString(FirebaseAnalytics.Param.ITEM_BRAND, item.itemBrand)
            putDouble(FirebaseAnalytics.Param.PRICE, item.itemPrice!!.toDouble())
        }

        val itemBundleWithQuantity = Bundle(itemBundle).apply {
            putLong(FirebaseAnalytics.Param.QUANTITY, 1)
        }

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART) {
                param(FirebaseAnalytics.Param.CURRENCY, "USD")
                param(FirebaseAnalytics.Param.VALUE, item.itemPrice!!.toDouble())
                param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemBundleWithQuantity))
            }
    }

    private fun logFirebaseViewCart(list: List<CartUiModel>, countPrice: Int) {
        Timber.d("LogFirebaseViewCart")
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
            val itemBundleWithQuantity = Bundle(itemBundle).apply {
                putLong(FirebaseAnalytics.Param.QUANTITY, 1)
            }
            arrBundle.add(itemBundleWithQuantity)
        }

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.VIEW_CART) {
                param(FirebaseAnalytics.Param.CURRENCY, "USD")
                param(FirebaseAnalytics.Param.VALUE, countPrice.toDouble())
                param(FirebaseAnalytics.Param.ITEMS, arrBundle.toTypedArray())
            }
    }
}