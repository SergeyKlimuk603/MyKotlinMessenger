package by.klimuk.mykotlinmessenger.utilites

import by.klimuk.mykotlinmessenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Файл для работы с базой данных
 */

// Переменная аутентификации пользователя
lateinit var AUTH: FirebaseAuth

// Ссылка на базу данных
lateinit var REF_DATABASE_ROOT: DatabaseReference

lateinit var USER: User
lateinit var UID: String

const val NODE_APP = "MyKotlinMessenger"
const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference.child(NODE_APP)
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}

