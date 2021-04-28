package com.arifrgilang.presentation.util.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * Created by arifrgilang on 4/14/2021
 */
abstract class BaseBindingActivity<T : ViewDataBinding> : AppCompatActivity(){

    @LayoutRes
    protected abstract fun contentView(): Int
    protected abstract fun setupData(savedInstanceState: Bundle?)
    protected abstract fun setupView()
    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentView())
        setupData(savedInstanceState)
        setupView()
    }
}