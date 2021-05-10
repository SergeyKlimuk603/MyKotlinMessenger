package by.klimuk.mykotlinmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import by.klimuk.mykotlinmessenger.databinding.ActivityMainBinding
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding      // связки
    private lateinit var mDrawer: Drawer                    // выдвижная панель
    private lateinit var mHeader: AccountHeader             // заголовок(шапка) выдвижной панели
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
    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        createHeader()
        createDrawer()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()                // заголовок выдвижной панели
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)    // фон заголовка выдвижной панели
            .addProfiles(
                ProfileDrawerItem().withName("Sergey").withEmail("+000000000")
            )
            .build()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(this)
            .withToolbar(mToolbar)                                          // добавляем возможность вызова выдвижной панели из тулбара
            //.withActionBarDrawerToggle(true)       // не знаю зачем нужна эта строка (автор говорит, что это иконка сэндвич в тубаре, но она есть и без этой строки)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(                                                // добавляем элемент списка в выдвижную панель
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Создать группу")
                    .withSelectable(false)
            ).build()
    }


}