package by.klimuk.mykotlinmessenger.ui.fragments

import androidx.fragment.app.Fragment
import by.klimuk.mykotlinmessenger.MainActivity
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.utilities.AUTH
import by.klimuk.mykotlinmessenger.utilities.replaceActivity
import by.klimuk.mykotlinmessenger.utilities.replaceFragment
import by.klimuk.mykotlinmessenger.utilities.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

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
import java.util.concurrent.TimeUnit

/**
 * Фрагмент воода номера телефона пользователя при входе аутентификации
 *
 */

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    //Номер телефона пользователя для регистрации в приложении
    private lateinit var phoneNumber: String

    //Объект обратного вызова при верификации телефона
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        super.onStart()
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.register_toast_welcome))
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else {
                        showToast(it.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                replaceFragment(R.id.registerDataContainer, EnterCodeFragment(phoneNumber, id))
            }
        }

        //Получаем слушателя кнопки register_btn_next
        register_btn_next.setOnClickListener { sendCode() }
    }

    //Если номер телефона введен корректно, то отправляем его на верификацию
    private fun sendCode() {
        phoneNumber = register_input_phone_number.text.toString()
        if (phoneNumber.isEmpty()) {
            showToast(getString(R.string.register_text_enter_phone))
        } else {
            authUser()
        }
    }

    //Верификация номера пользователя
    private fun authUser() {
        val options = PhoneAuthOptions.newBuilder(AUTH)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity as RegisterActivity)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}