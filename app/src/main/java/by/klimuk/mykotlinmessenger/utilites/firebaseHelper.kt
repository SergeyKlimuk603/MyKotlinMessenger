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

// Ссылка на корневую папку в базе данных
lateinit var REF_DATABASE_ROOT: DatabaseReference

// Модель текущего пользователя с основыми данными
lateinit var USER: User

lateinit var UID: String

// Корневая папка приложения в базе данных
const val NODE_APP = "MyKotlinMessenger"

// Нода всех пользователей
const val NODE_USERS = "users"

// Нода для организации уникальности username пользователей
const val NODE_USERNAMES = "usernames"

// Данные пользователя в базе данных
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

