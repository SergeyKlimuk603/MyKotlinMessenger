package by.klimuk.mykotlinmessenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.databinding.ActivityMainBinding
import by.klimuk.mykotlinmessenger.models.User
import by.klimuk.mykotlinmessenger.ui.fragments.ChatFragment
import by.klimuk.mykotlinmessenger.ui.objects.AppDrawer
import by.klimuk.mykotlinmessenger.utilites.*
import com.theartofdev.edmodo.cropper.CropImage

/**
 * Главный экран приложения
 */

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding      // связки
    lateinit var mAppDrawer: AppDrawer                      // выдвижная панель ее нельзя делать private т.к. к ней идет обращение извне
    private lateinit var mToolbar: Toolbar                  // тулбар MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this        // замена строки (activity as MainActivity)
        initField()       //Инициализируем поля главного экрана
        initFunc()        //Инициализируем функциональность полей главного экрана
    }

    //Инициализируем поля главного экрана
    private fun initField() {
        mToolbar = mBinding.mainToolbar                     // Toolbar главного экрана
        mAppDrawer = AppDrawer(this, mToolbar)   // Объект создания выдвижной панели
        initFirebase()
        initUser()
    }


    //Инициализируем функциональность полей главного экрана
    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            //Добавляем фрагмент чатов в контейнер фрагментов MainActivity
            replaceFragment(R.id.dataContainer, ChatFragment(), false)
        } else {
            //Запускаем регистрацию пользователя
            replaceActivity(RegisterActivity())
        }

    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java)
                    ?: User() // если при получении пользователя имеем null то инициализируем переменную пустым пользователем
            })
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
            // отправляем файл в базу данных
            path.putFile(uri).addOnCompleteListener() { task1 ->
                if (task1.isSuccessful) {
                    // запрашивает url изображения в хранилище базы данных
                    path.downloadUrl.addOnCompleteListener() { task2 ->
                        if (task2.isSuccessful) {
                            val photoIrl = task2.result.toString()
                            // записываем url изображения в базу данных
                            REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
                                .child(CHILD_PHOTO_URL).setValue(photoIrl)
                                .addOnCompleteListener() {
                                    if (it.isSuccessful) {
                                        USER.photoUrl = photoIrl
                                        showToast(getString(R.string.toast_date_update))
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }


}