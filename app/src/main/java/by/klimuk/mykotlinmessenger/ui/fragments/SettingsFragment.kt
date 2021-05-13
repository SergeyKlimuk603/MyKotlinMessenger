package by.klimuk.mykotlinmessenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }
}