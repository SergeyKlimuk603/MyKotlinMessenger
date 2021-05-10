package by.klimuk.mykotlinmessenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.klimuk.mykotlinmessenger.databinding.ActivityMainBinding
import by.klimuk.mykotlinmessenger.ui.fragments.ChatFragment
import by.klimuk.mykotlinmessenger.ui.objects.AppDriver

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding      // связки
    private lateinit var mAppDriver: AppDriver              // выдвижная панель
    private lateinit var mToolbar: Toolbar                  // тулбар MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initField()
        initFunc()
    }

    private fun initField() {
        mToolbar = mBinding.mainToolbar
        mAppDriver = AppDriver(this, mToolbar)
    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        mAppDriver.create()
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer, ChatFragment()).commit()
    }




}