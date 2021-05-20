package by.klimuk.mykotlinmessenger.utilites

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * Вспомогательный класс является базовым для создания
 * слушателей базы данных
 */

class AppValueEventListener(val onSuccess: (DataSnapshot) -> Unit) : ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
       onSuccess(snapshot)
    }

    override fun onCancelled(error: DatabaseError) {

    }
}