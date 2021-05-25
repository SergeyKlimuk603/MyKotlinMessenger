package by.klimuk.mykotlinmessenger.utilities

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.klimuk.mykotlinmessenger.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Реализация функционала приложения используемого во многих
 * частях кода
 */

// Всплывающие сообщения
fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
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
// убираем клавиатуру с экрана
fun hideKeyboard() {
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

// загрузка изображений из базы данных
fun CircleImageView.downloadAndSetImage(url: String) {
    Picasso.get()
        .load(url)                                // url с которого необходимо скачать изображение
        .placeholder(R.drawable.default_photo)    // картинка по умолчанию (например, если нет доступа в интернет)
        .into(this)                         // куда помещается изображение
}