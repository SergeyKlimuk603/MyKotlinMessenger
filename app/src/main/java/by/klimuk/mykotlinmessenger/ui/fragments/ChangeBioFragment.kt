package by.klimuk.mykotlinmessenger.ui.fragments

import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.utilities.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

/**
 * Фрагмент заполнения информации о пользователе
 */

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_date_update))
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }
            }
    }
}