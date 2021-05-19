package by.klimuk.mykotlinmessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import by.klimuk.mykotlinmessenger.MainActivity
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.utilites.AUTH
import by.klimuk.mykotlinmessenger.utilites.USER
import by.klimuk.mykotlinmessenger.utilites.replaceActivity
import by.klimuk.mykotlinmessenger.utilites.replaceFragment
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * Фрагмент настроек приложения
 *
 */

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        settings_username.text = USER.username
    }

    // Cоздаем меню настроек фрагмента
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    //Обработка нажатий пунктов меню Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(R.id.dataContainer, ChangeNameFragment())

        }
        return true
    }
}