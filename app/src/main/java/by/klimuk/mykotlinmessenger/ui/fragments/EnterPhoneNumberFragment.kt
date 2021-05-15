package by.klimuk.mykotlinmessenger.ui.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.utilites.replaceFragment
import by.klimuk.mykotlinmessenger.utilites.showToast

/* Чтобы появился этот импорт нужно добавить строку
id 'kotlin-android-extensions'
в раздел
plugins
в "build.gradle(:app)"
Это нужно для поиска view в макете

plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-android-extensions'
}
 */
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    override fun onStart() {
        super.onStart()
        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()) {
            showToast(getString(R.string.register_text_enter_phone))
        } else {
            replaceFragment(R.id.registerDataContainer, EnterCodeFragment())
        }
    }

}