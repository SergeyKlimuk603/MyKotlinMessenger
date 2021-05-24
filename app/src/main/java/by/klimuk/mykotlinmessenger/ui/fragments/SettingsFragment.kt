package by.klimuk.mykotlinmessenger.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import by.klimuk.mykotlinmessenger.MainActivity
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.utilites.*
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
    }

    // изменение фото пользователя
    // результаты работы этого метода нужно искать в MainActivity в функции onActivityResult()
    private fun changePhotoUser() {
        // вызов активити для работы с изображениями
        CropImage.activity()                              // класс для работы с изображениями (фото пользователя)
            .setAspectRatio(1, 1)   // пропорции полученного изображения
            .setRequestedSize(200, 200)  // максимальный размер изображения
            .setCropShape(CropImageView.CropShape.OVAL)     // форма изображения - овальная
            .start((APP_ACTIVITY))                         // (APP_ACTIVITY) = (activity as MainActivity)
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