package com.arifrgilang.presentation.ui.login

import android.os.Bundle
import com.arifrgilang.presentation.ui.MainActivity
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.databinding.FragmentLoginBinding
import com.arifrgilang.presentation.util.base.BaseBindingFragment

class LoginFragment : BaseBindingFragment<FragmentLoginBinding>() {
    override fun contentView(): Int = R.layout.fragment_login

    override fun setupData(savedInstanceState: Bundle?) {}

    override fun setupView() {
        binding.btnLogin.setOnClickListener() {
            (activity as MainActivity).performLogin()
        }
    }
}