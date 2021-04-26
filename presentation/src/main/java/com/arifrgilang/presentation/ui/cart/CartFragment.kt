package com.arifrgilang.presentation.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentCartBinding
import com.arifrgilang.presentation.util.base.BaseBindingFragment


class CartFragment : BaseBindingFragment<FragmentCartBinding>() {
    override fun contentView(): Int = R.layout.fragment_cart
    override fun setupData(savedInstanceState: Bundle?) {}
    override fun setupView() {}
}