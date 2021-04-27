package com.arifrgilang.presentation.ui.checkout

import android.content.Context
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ItemCheckoutBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.model.CheckoutUiModel
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter


/**
 * Created by arifrgilang on 4/27/2021
 */
class CheckoutRvAdapter(
    context: Context
) : BaseRecyclerAdapter<CheckoutUiModel, ItemCheckoutBinding, CheckoutRvAdapter.ViewHolder>(context) {
    private var callbackPay: OnPayClicked? = null

    fun setPositiveCallback(listener: OnPayClicked){
        callbackPay = listener
    }

    inner class ViewHolder(view: ItemCheckoutBinding) : BaseViewHolder(view) {
        override fun onBind(model: CheckoutUiModel) {
            view.itemId = model.id
            view.itemCount = model.items.size
            view.itemPrice = countItemPrice(model.items)
            view.btnCheckoutDelete.setOnClickListener {
                getCallback()?.onRecyclerItemClicked(model.id.toString())
            }
            view.btnChekoutPay.setOnClickListener {
                callbackPay?.onAgree(model)
            }
        }
    }

    override fun getResLayout(type: Int): Int = R.layout.item_checkout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(initViewBinding(viewType, parent))

    private fun countItemPrice(items: List<CartUiModel>): Int {
        var result = 0
        for(item in items){
            result+=item.itemPrice!!.toInt()
        }
        return result
    }

    interface OnPayClicked{
        fun onAgree(item: CheckoutUiModel)
    }
}