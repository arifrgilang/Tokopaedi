package com.arifrgilang.presentation.ui.historydetail

import android.content.Context
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ItemCheckoutDetailBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter


/**
 * Created by arifrgilang on 4/27/2021
 */
class HistoryDetailRvAdapter(
    context: Context
) : BaseRecyclerAdapter<CartUiModel, ItemCheckoutDetailBinding, HistoryDetailRvAdapter.ViewHolder>(context) {
    inner class ViewHolder(view: ItemCheckoutDetailBinding) : BaseViewHolder(view) {
        override fun onBind(model: CartUiModel) {
            view.itemCart = model
        }
    }

    override fun getResLayout(type: Int): Int  = R.layout.item_checkout_detail

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(initViewBinding(viewType, parent))
}