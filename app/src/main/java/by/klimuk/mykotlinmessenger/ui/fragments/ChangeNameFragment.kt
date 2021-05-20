package by.klimuk.mykotlinmessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import by.klimuk.mykotlinmessenger.MainActivity
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.utilites.*
import kotlinx.android.synthetic.main.fragment_change_name.*

/**
 * Фрагмент для изменения имени и фамилии пользователя
 */

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        initFullnameList()
    }

    private fun initFullnameList() {
        val fullnameList = USER.fullname.split(" ")
        settings_input_name.setText(fullnameList[0])
        if (fullnameList.size > 1) {
            settings_input_surname.setText(fullnameList[1])
        }
    }


    override fun change() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener{
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_date_update))
                        USER.fullname = fullname
                        // возвращаемся по стеку назад
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}