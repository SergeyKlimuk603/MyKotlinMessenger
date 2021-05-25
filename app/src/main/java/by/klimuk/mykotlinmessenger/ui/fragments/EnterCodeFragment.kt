package by.klimuk.mykotlinmessenger.ui.fragments

import by.klimuk.mykotlinmessenger.MainActivity
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.utilities.*
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

/**
 * Фрагмент ввода проверочного кода из SMS при аутентификации пользователя в Firebase
 */

class EnterCodeFragment(val phoneNumber: String, val id: String) : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber

        //Присваиваем полю ввода кода из SMS слушателя,
        //который проверяет количество введенных символов
        register_input_code.addTextChangedListener(AppTextWatcher {
                val code = register_input_code.text.toString()
                if (code.length == 6) {
                    enterCode(code)
                }
        })
    }

    private fun enterCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener() {
            if (it.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid
                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            showToast(getString(R.string.register_toast_welcome))
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else {
                            showToast(task.exception?.message.toString())
                        }
                    }
                showToast(it.exception?.message.toString())
            }
        }
    }
}