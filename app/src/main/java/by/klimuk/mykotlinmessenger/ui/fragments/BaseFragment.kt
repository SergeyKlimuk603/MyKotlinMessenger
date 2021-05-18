package by.klimuk.mykotlinmessenger.ui.fragments

import androidx.fragment.app.Fragment

/**
 * Фрагмент нужен был для передачи макета во фрагмент,
 * но, похоже он уже не нужен и можно наследоваться не
 * от этого фрагмента а просто от Fragment() с передачей
 * макета в конструктор
 */

open class BaseFragment(val layout: Int) : Fragment(layout) {

}