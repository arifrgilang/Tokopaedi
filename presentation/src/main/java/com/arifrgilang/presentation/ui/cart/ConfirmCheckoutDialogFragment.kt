package com.arifrgilang.presentation.ui.cart

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.ui.dashboard.LogoutDialogFragment


/**
 * Created by arifrgilang on 4/27/2021
 */
class ConfirmCheckoutDialogFragment(
    private val callback: ConfirmCheckoutDialogFragment.DialogCallback
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.dialog_confirm_checkout, null)
            .apply {
                val btnYes = this.findViewById<Button>(R.id.btn_confirm_check_yes)
                val btnNo = this.findViewById<Button>(R.id.btn_confirm_check_no)

                btnYes.setOnClickListener {
                    callback.isAgree()
                    dismiss()
                }

                btnNo.setOnClickListener {
                    dismiss()
                }
            }

        val builder = AlertDialog.Builder(requireContext()).setView(view)
        return builder.create().apply { setCanceledOnTouchOutside(false) }
    }

    interface DialogCallback {
        fun isAgree()
    }
}