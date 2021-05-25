package by.klimuk.mykotlinmessenger.utilities

import android.net.Uri
import by.klimuk.mykotlinmessenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Файл для работы с базой данных
 */

// Переменная аутентификации пользователя
lateinit var AUTH: FirebaseAuth

// Ссылка на корневую папку в базе данных
lateinit var REF_DATABASE_ROOT: DatabaseReference

// Ссылка на корневую папку в хранилище базы данных
lateinit var REF_STORAGE_ROOT: StorageReference

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
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photoUrl"

const val FOLDER_APP = "MyKotlinMessenger"
const val FOLDER_PROFILE_IMAGE = "profile_image"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference.child(NODE_APP)
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference.child(FOLDER_APP)
}

// inline функции приводят к увеличению производительности т.к. не создается объект функции,
// а просто в нужном месте подставляется ее код

// отправляем файл в базу данных
inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }      // это замена onCompleteListener, которая срабатывает только после успешного выполнения задания
        .addOnFailureListener { showToast(it.message.toString()) }
}

// запрашиваем url изображения в хранилище базы данных
inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener{ showToast(it.message.toString()) }
}

// записываем url изображения в базу данных
inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
        .child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener{ showToast(it.message.toString()) }
}

