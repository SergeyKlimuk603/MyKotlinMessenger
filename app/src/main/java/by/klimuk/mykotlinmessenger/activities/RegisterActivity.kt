package by.klimuk.mykotlinmessenger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.databinding.ActivityRegisterBinding
import by.klimuk.mykotlinmessenger.ui.fragments.EnterPhoneNumberFragment
import by.klimuk.mykotlinmessenger.utilites.replaceFragment

/**
 * Аутентификация пользователя в Firebase.
 * Класс содержит контейнер для фрагментов аутентификации
 */

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(R.id.registerDataContainer, EnterPhoneNumberFragment(), false)
    }

    override fun onStart() {
        super.onStart()
    }
}