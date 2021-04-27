package com.arifrgilang.presentation.ui.promodetail

import android.content.Context
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ItemClothesBinding
import com.arifrgilang.presentation.databinding.ItemClothesGridBinding
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter


/**
 * Created by arifrgilang on 4/26/2021
 */
class PromoDetailRvAdapter(
    context: Context
) : BaseRecyclerAdapter<ItemUiModel, ItemClothesGridBinding, PromoDetailRvAdapter.ViewHolder>(context) {

    inner class ViewHolder(view: ItemClothesGridBinding) : BaseViewHolder(view) {
        override fun onBind(model: ItemUiModel) {
            view.item = model
            view.cvItemClothes.setOnClickListener {
                getCallback()?.onRecyclerItemClicked(model.id.toString())
            }
        }
    }

    override fun getResLayout(type: Int): Int = R.layout.item_clothes_grid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(initViewBinding(viewType, parent))
}