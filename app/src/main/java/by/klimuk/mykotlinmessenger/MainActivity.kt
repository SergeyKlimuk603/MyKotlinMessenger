package by.klimuk.mykotlinmessenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.klimuk.mykotlinmessenger.activities.RegisterActivity
import by.klimuk.mykotlinmessenger.databinding.ActivityMainBinding
import by.klimuk.mykotlinmessenger.ui.fragments.ChatFragment
import by.klimuk.mykotlinmessenger.ui.objects.AppDrawer
import by.klimuk.mykotlinmessenger.utilites.AUTH
import by.klimuk.mykotlinmessenger.utilites.replaceActivity
import by.klimuk.mykotlinmessenger.utilites.replaceFragment
import com.google.firebase.auth.FirebaseAuth

/**
 * Главный экран приложения
 */

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding      // связки
    private lateinit var mAppDrawer: AppDrawer              // выдвижная панель
    private lateinit var mToolbar: Toolbar                  // тулбар MainActivity
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initField()       //Инициализируем поля главного экрана
        initFunc()        //Инициализируем функциональность полей главного экрана
    }

    //Инициализируем поля главного экрана
    private fun initField() {
        mToolbar = mBinding.mainToolbar                     // Toolbar главного экрана
        mAppDrawer = AppDrawer(this, mToolbar)   // Объект создания выдвижной панели
        AUTH = FirebaseAuth.getInstance()
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




}