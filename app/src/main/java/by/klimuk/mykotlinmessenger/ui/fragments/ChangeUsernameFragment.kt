package by.klimuk.mykotlinmessenger.ui.fragments

import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.utilites.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

/**
 * Фрагмент для изменения username пользователя и для проверки его уникальности
 */

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }

    override fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast("Такой пользователь уже существует")
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    // добавляем username в список уникальных имен пользователей
    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    // обновляем username в базе данных
    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    deleteOldUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    // удаляем старый username
    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                    showToast(getString(R.string.toast_date_update))
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}