package com.arifrgilang.presentation.ui.cart

import android.content.Context
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ItemCartBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter


/**
 * Created by arifrgilang on 4/27/2021
 */
class CartRvAdapter(
    context: Context
) : BaseRecyclerAdapter<CartUiModel, ItemCartBinding, CartRvAdapter.ViewHolder>(context) {

    override fun getResLayout(type: Int): Int = R.layout.item_cart

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(initViewBinding(viewType, parent))

    inner class ViewHolder(view: ItemCartBinding) : BaseViewHolder(view) {
        override fun onBind(model: CartUiModel) {
            view.itemCart = model
            view.ivDeleteFromCart.setOnClickListener {
                getCallback()?.onRecyclerItemClicked(model.id.toString())
            }
        }
    }
}