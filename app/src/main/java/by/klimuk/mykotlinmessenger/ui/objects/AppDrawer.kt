package by.klimuk.mykotlinmessenger.ui.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import by.klimuk.mykotlinmessenger.R
import by.klimuk.mykotlinmessenger.ui.fragments.SettingsFragment
import by.klimuk.mykotlinmessenger.utilites.replaceFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

/**
 * Задача класса - создание левой выдвижной панели, заполнение
 * ее элементами меню и обработка нажатий на элементы меню
 */

class AppDrawer(val mainActivity: AppCompatActivity, val toolbar: Toolbar) {

    private lateinit var mDrawer: Drawer                    // выдвижная панель
    private lateinit var mHeader: AccountHeader             // заголовок(шапка) выдвижной панели
    private lateinit var mDrawerLayout: DrawerLayout        // слой Drawer для блокировки выдвижной панели

    //Создаем выдвижную панель
    fun create() {
        createHeader()                                      // Создаем заголовок выдвижного меню
        createDrawer()                                      // Создаем выдвижную панель
        mDrawerLayout = mDrawer.drawerLayout
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()                // заголовок выдвижной панели
            .withActivity(mainActivity)
            .withHeaderBackground(R.drawable.header)    // фон заголовка выдвижной панели
            .addProfiles(
                ProfileDrawerItem().withName("Sergey").withEmail("+000000000")
            )
            .build()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)                                     // добавляем возможность вызова выдвижной панели из тулбара
            //.withActionBarDrawerToggle(true)                        // не знаю зачем нужна эта строка (автор говорит, что это иконка сэндвич в тубаре, но она есть и без этой строки)
            .withSelectedItem(-1)                   //запрещаем выбирать пункт меню
            .withAccountHeader(mHeader)                               //передаем заголовок
            .addDrawerItems(                                          // добавляем элементы списка в выдвижную панель
                getDrawerItem(100, "Создать группу", R.drawable.ic_menu_create_groups),
                getDrawerItem(101, "Создать секретный чат", R.drawable.ic_menu_secret_chat),
                getDrawerItem(102, "Создать канал", R.drawable.ic_menu_create_channel),
                getDrawerItem(103, "Контакты", R.drawable.ic_menu_contacts),
                getDrawerItem(104, "Звонки", R.drawable.ic_menu_phone),
                getDrawerItem(105, "Избранное", R.drawable.ic_menu_favorites),
                getDrawerItem(106, "Настройки", R.drawable.ic_menu_settings),
                DividerDrawerItem(),
                getDrawerItem(107, "Пригласить друзей", R.drawable.ic_menu_invate),
                getDrawerItem(108, "Вопросы о приложении", R.drawable.ic_menu_help)
            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?, position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    chooseFragment(position)
                    return false
                }
            })
            .build()
    }

    //Создание отдельного элемента меню выдвижной панели
    private fun getDrawerItem(id: Long, name: String, iconId: Int): IDrawerItem<*> {
        return PrimaryDrawerItem()
            .withIdentifier(id)                             // id пункта
            .withIconTintingEnabled(true)
            .withName(name)
            .withSelectable(false)                 //выбран ли элемент
            .withIcon(iconId)
    }

    //Выбираем фрагмент согласно нажатого элемента меню выдвижной панели
    private fun chooseFragment(pos: Int) {
        when (pos) {
            7 -> mainActivity.replaceFragment(R.id.dataContainer, SettingsFragment())
        }
    }

    // Активация Drawer
    fun disableDrawer() {
        // отключаем кнопку вызова Drawer в Toolbar
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        // включаем кнопку возврата в Toolbar (дублирует кнопку возврата в Android)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // блокируем выдвижную панель в спрятанном состоянии
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        // переопределяем поведение кнопки возврата в toolbar
        toolbar.setNavigationOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()    // возвращаемся по стеку назад
        }
    }

    // Дизактивация Drawer
    fun enabledDrawer() {
        // выключаем кнопку возврата в Toolbar (дублирует кнопку возврата в Android)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        // включаем кнопку вызова Drawer в Toolbar
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        // блокируем выдвижную панель в спрятанном состоянии
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        // переопределяем поведение кнопки возврата в toolbar
        toolbar.setNavigationOnClickListener {
            mDrawer.openDrawer()                                    // отображаем Drawer
        }
    }
}