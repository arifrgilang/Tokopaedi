package com.arifrgilang.presentation.ui.checkout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.arifrgilang.presentation.R


/**
 * Created by arifrgilang on 4/27/2021
 */
class ConfirmPayDialogFragment(
    private val callback: DialogCallback
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.dialog_confirm_pay, null)
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