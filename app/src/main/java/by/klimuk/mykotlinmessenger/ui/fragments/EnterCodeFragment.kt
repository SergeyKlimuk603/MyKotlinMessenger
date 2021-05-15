package by.klimuk.mykotlinmessenger.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.utilites.AppTextWatcher
import by.klimuk.mykotlinmessenger.utilites.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        register_input_code.addTextChangedListener(AppTextWatcher {
                val string = register_input_code.text.toString()
                if (string.length == 6) {
                    verifiCode()
                }
        })
    }

    private fun verifiCode() {
        showToast("Ok")
    }
}