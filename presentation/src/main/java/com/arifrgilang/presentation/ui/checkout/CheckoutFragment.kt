package com.arifrgilang.presentation.ui.checkout

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentCheckoutBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.model.CheckoutUiModel
import com.arifrgilang.presentation.util.CustomRvMargin
import com.arifrgilang.presentation.util.base.BaseBindingFragment
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.event.observeEvent
import com.arifrgilang.presentation.util.view.toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class CheckoutFragment : BaseBindingFragment<FragmentCheckoutBinding>() {
    private val viewModel by viewModel<CheckoutViewModel>()
    private val rvAdapter: CheckoutRvAdapter by inject()

    override fun contentView(): Int = R.layout.fragment_checkout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModelObservers()
    }

    override fun setupData(savedInstanceState: Bundle?) {
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
                        logFirebasePurchase(item)
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

    private fun logFirebasePurchase(checkoutModel: CheckoutUiModel) {
        Timber.d("LogFirebasePurchase")
        val arrBundle = mutableListOf<Bundle>()

        for(item in checkoutModel.items){
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

        val checkoutId = checkoutModel.id.toString() + "_" + UUID.randomUUID()
        val price = countPrice(checkoutModel.items).toDouble()
        val tax: Double = price * 0.05 // 5% tax
        val shipping: Double = 1.0 // 1 USD

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.PURCHASE) {
                param(FirebaseAnalytics.Param.TRANSACTION_ID, checkoutId)
                param(FirebaseAnalytics.Param.AFFILIATION, "Opaku Shop")
                param(FirebaseAnalytics.Param.CURRENCY, "USD")
                param(FirebaseAnalytics.Param.VALUE, price)
                param(FirebaseAnalytics.Param.TAX, tax)
                param(FirebaseAnalytics.Param.SHIPPING, shipping)
                param(FirebaseAnalytics.Param.COUPON, "NO_COUPON")
                param(FirebaseAnalytics.Param.ITEMS, arrBundle.toTypedArray())
            }
    }
}