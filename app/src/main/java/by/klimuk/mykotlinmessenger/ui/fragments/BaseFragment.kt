package by.klimuk.mykotlinmessenger.ui.fragments

import androidx.fragment.app.Fragment
import by.klimuk.mykotlinmessenger.MainActivity

/**
 * Фрагмент нужен был для передачи макета во фрагмент,
 * но, похоже он уже не нужен и можно наследоваться не
 * от этого фрагмента а просто от Fragment() с передачей
 * макета в конструктор
 */

open class BaseFragment(val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).mAppDrawer.enabledDrawer()
    }
}