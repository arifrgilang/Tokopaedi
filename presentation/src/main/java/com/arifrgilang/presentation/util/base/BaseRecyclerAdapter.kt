package com.arifrgilang.presentation.util.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by arifrgilang on 4/14/2021
 */
abstract class BaseRecyclerAdapter<
        Model,
        VB: ViewDataBinding,
        VH : BaseRecyclerAdapter<Model, VB, VH>.BaseViewHolder>(
    protected var context: Context?
) : RecyclerView.Adapter<VH>() {
    private var modelList = mutableListOf<Model>()
    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var recyclerCallback: AdapterOnClick? = null

    private val activity: Activity?
        get() = context as? Activity

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.onBind(modelList[position]) // reversed order insert

    override fun getItemCount(): Int = modelList.size

    protected fun initViewBinding(viewType: Int, parent: ViewGroup): VB =
        DataBindingUtil
            .inflate(
                inflater,
                getResLayout(viewType),
                parent,
                false
            )

    protected abstract fun getResLayout(type: Int): Int

    fun setOnItemClickListener(listener: AdapterOnClick){
        recyclerCallback = listener
    }

    protected fun getCallback() = recyclerCallback

    fun insertAndNotify(models: List<Model>?) {
        if (models != null && models.isNotEmpty()) {
            modelList.addAll(models)
            activity?.runOnUiThread{
                if (modelList.size - models.size == 0) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeInserted(modelList.size - models.size, models.size)
                }
            }
        }
    }

    fun insertAndNotify(model: Model) {
        modelList.add(model)
        activity?.runOnUiThread{
            if (modelList.size == 1) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(modelList.size - 1, 1)
            }
        }
    }

    fun clearAndNotify() {
        modelList.clear()
        activity?.runOnUiThread {
            notifyDataSetChanged()
        }
    }

    abstract inner class BaseViewHolder (val view: VB): RecyclerView.ViewHolder(view.root) {
        abstract fun onBind(model: Model)
    }

    interface AdapterOnClick {
        fun onRecyclerItemClicked(extra: String)
    }
}