package com.arifrgilang.presentation.ui.dashboard

import android.content.Context
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ItemClothesBinding
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter


/**
 * Created by arifrgilang on 4/25/2021
 */
class DashboardRvAdapter(
    context: Context
) : BaseRecyclerAdapter<ItemUiModel, ItemClothesBinding, DashboardRvAdapter.ViewHolder>(context) {

    inner class ViewHolder(view: ItemClothesBinding) : BaseViewHolder(view) {
        override fun onBind(model: ItemUiModel) {
            view.item = model
            view.llItemClothes.setOnClickListener {
                getCallback()?.onRecyclerItemClicked(model.id.toString())
            }
        }
    }

    override fun getResLayout(type: Int): Int = R.layout.item_clothes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(initViewBinding(viewType, parent))
}