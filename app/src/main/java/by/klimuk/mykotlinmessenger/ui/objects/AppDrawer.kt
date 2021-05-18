package by.klimuk.mykotlinmessenger.ui.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

/**
 * Задача класса - создание левой выдвижной панели, заполнение
 * ее элементами меню и обработка нажатий на элементы меню
 */

class AppDrawer(val mainActivity: AppCompatActivity, val toolbar: Toolbar) {

    private lateinit var mDrawer: Drawer                    // выдвижная панель
    private lateinit var mHeader: AccountHeader             // заголовок(шапка) выдвижной панели

    //Создаем выдвижную панель
    fun create() {
        createHeader()                                      //Создаем заголовок выдвижного меню
        createDrawer()                                      //Создаем выдвижную панель
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
                getPrimaryDrawerItem(100, "Создать группу",
                    R.drawable.ic_menu_create_groups),
                getPrimaryDrawerItem(101, "Создать секретный чат",
                    R.drawable.ic_menu_secret_chat),
                getPrimaryDrawerItem(102, "Создать канал",
                    R.drawable.ic_menu_create_channel),
                getPrimaryDrawerItem(103, "Контакты",
                    R.drawable.ic_menu_contacts),
                getPrimaryDrawerItem(104, "Звонки",
                    R.drawable.ic_menu_phone),
                getPrimaryDrawerItem(105, "Избранное",
                    R.drawable.ic_menu_favorites),
                getPrimaryDrawerItem(106, "Настройки",
                    R.drawable.ic_menu_settings),
                DividerDrawerItem(),
                getPrimaryDrawerItem(107, "Пригласить друзей",
                    R.drawable.ic_menu_invate),
                getPrimaryDrawerItem(108, "Вопросы о приложении",
                    R.drawable.ic_menu_help))
            .withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener{
                override fun onItemClick(view: View?, position: Int,
                                         drawerItem: IDrawerItem<*>): Boolean {
                    chooseFragment(position)
                    return false
                }
            })
            .build()
    }

    //Создание отдельного элемента меню выдвижной панели
    private fun getPrimaryDrawerItem(id: Long, name: String, iconId: Int): PrimaryDrawerItem {
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
}