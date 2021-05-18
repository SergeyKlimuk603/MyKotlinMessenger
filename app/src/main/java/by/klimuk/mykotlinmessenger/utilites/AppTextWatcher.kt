package by.klimuk.mykotlinmessenger.utilites

import android.text.Editable
import android.text.TextWatcher

/**
 * Вспомогательный класс является базовым для создания
 * слушателей изменения текста в текстовых полях (EditText).
 * Нужен для реализации неиспользуемых методов TextWatcher, для
 * уменьшения основного кода
 * Конкретная логика работы слушателя, определяется при создании
 * объекта, передачей лямбда-функции.
 */

class AppTextWatcher(val onSuccess: (Editable?) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        onSuccess(s)
    }
}