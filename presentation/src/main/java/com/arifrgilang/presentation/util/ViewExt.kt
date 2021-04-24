package com.arifrgilang.presentation.util

import android.content.Context
import android.widget.Toast


/**
 * Created by arifrgilang on 4/24/2021
 */
fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
