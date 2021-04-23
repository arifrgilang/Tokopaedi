package com.arifrgilang.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arifrgilang.presentation.databinding.ActivityLoginBinding
import com.arifrgilang.presentation.util.base.BaseBindingActivity

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_OpakuShop)
        super.onCreate(savedInstanceState)
    }

    override fun contentView(): Int = R.layout.activity_login

    override fun setupData(savedInstanceState: Bundle?) {}

    override fun setupView() {}
}