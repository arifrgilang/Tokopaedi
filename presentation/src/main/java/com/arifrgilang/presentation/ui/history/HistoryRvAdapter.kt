package com.arifrgilang.presentation.ui.history

import android.content.Context
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.ItemHistoryBinding
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.model.HistoryUiModel
import com.arifrgilang.presentation.util.base.BaseRecyclerAdapter


/**
 * Created by arifrgilang on 4/27/2021
 */
class HistoryRvAdapter(
    context: Context
) : BaseRecyclerAdapter<HistoryUiModel, ItemHistoryBinding, HistoryRvAdapter.ViewHolder>(context){
    inner class ViewHolder(view: ItemHistoryBinding) : BaseViewHolder(view) {
        override fun onBind(model: HistoryUiModel) {
            view.itemId = model.id
            view.itemCount = model.items.size
            view.itemPrice = countItemPrice(model.items)
            view.cvItemHistory.setOnClickListener {
                getCallback()?.onRecyclerItemClicked(model.id.toString())
            }
        }
    }

    override fun getResLayout(type: Int): Int = R.layout.item_history

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(initViewBinding(viewType, parent))

    private fun countItemPrice(items: List<CartUiModel>): Int {
        var result = 0
        for(item in items){
            result+=item.itemPrice!!.toInt()
        }
        return result
    }
}