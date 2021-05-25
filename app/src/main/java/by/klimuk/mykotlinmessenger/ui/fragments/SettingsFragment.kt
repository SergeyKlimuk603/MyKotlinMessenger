package by.klimuk.mykotlinmessenger.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import by.klimuk.mykotlinmessenger.MainActivity
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.utilities.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * Фрагмент настроек приложения
 */

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        settings_username.text = USER.username
        settings_btn_change_username.setOnClickListener {
            replaceFragment(R.id.dataContainer, ChangeUsernameFragment())
        }
        settings_btn_change_bio.setOnClickListener {
            replaceFragment(R.id.dataContainer, ChangeBioFragment())
        }
        settings_change_photo.setOnClickListener {changePhotoUser()}
        settings_user_photo.downloadAndSetImage(USER.photoUrl)
    }

    // изменение фото пользователя
    // результаты работы этого метода нужно искать в MainActivity в функции onActivityResult()
    private fun changePhotoUser() {
        // вызов активити для работы с изображениями
        CropImage.activity()                              // класс для работы с изображениями (фото пользователя)
            .setAspectRatio(1, 1)   // пропорции полученного изображения
            .setRequestedSize(200, 200)  // максимальный размер изображения
            .setCropShape(CropImageView.CropShape.OVAL)    // форма изображения - овальная
            .start(APP_ACTIVITY, this)          // (APP_ACTIVITY) = (activity as MainActivity)
    }

    // Сюда приходит ответ от работы других (сторонних) активностей, например из активности обработки изображения в фрагменте SettingsFragment
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Получаем ответ от CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null
        ) {
            // получаем uli выбранного изображения (или фрагмента изображения)
            val uri = CropImage.getActivityResult(data).uri
            // прописываем путь к папке в хранилище базы данных с фото пользователей
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(UID)

            // все эти функции прописаны в utilities\appDatabaseHelper
            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        settings_user_photo.downloadAndSetImage(it)
                        USER.photoUrl = it
                        showToast(getString(R.string.toast_date_update))
                    }
                }
            }
            
            /*
            // отправляем файл в базу данных
            path.putFile(uri).addOnCompleteListener() { task1 ->
                if (task1.isSuccessful) {
                    // запрашиваем url изображения в хранилище базы данных
                    path.downloadUrl.addOnCompleteListener() { task2 ->
                        if (task2.isSuccessful) {
                            val photoIrl = task2.result.toString()
                            // записываем url изображения в базу данных
                            REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
                                .child(CHILD_PHOTO_URL).setValue(photoIrl)
                                .addOnCompleteListener() {
                                    if (it.isSuccessful) {
                                        // загружаем картинку в профиль пользователя c помощью функции расширения
                                        settings_user_photo.downloadAndSetImage(photoIrl)
                                        USER.photoUrl = photoIrl
                                        showToast(getString(R.string.toast_date_update))
                                    }
                                }
                        }
                    }
                }
            }
            */
            
        }
    }

    // Cоздаем меню настроек фрагмента
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    //Обработка нажатий пунктов меню Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(R.id.dataContainer, ChangeNameFragment())

        }
        return true
    }
}