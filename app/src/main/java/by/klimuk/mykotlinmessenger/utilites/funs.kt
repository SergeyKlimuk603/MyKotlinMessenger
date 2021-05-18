package by.klimuk.mykotlinmessenger.utilites

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Реализация функционала приложения используемого во многих
 * частях кода
 */

// Всплывающие сообщения фрагментов
fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

// Смена активностей
fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    finish()
}

// Смена фрагмента из актевности
fun AppCompatActivity.replaceFragment(container: Int, fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(container, fragment)
            .commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(container, fragment)
            .commit()
    }

}

// Смена фрагмента из фрагмента
fun Fragment.replaceFragment(container: Int, fragment: Fragment) {
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(container, fragment)
        ?.commit()
}