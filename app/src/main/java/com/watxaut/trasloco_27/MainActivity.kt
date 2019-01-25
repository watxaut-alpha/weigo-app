package com.watxaut.trasloco_27

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.builders.footer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.badgeable.secondaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var result: Drawer
    private lateinit var headerResult: AccountHeader


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        result = drawer {
            toolbar = this@MainActivity.toolbar

            headerResult = accountHeader {
                background = R.drawable.header
                savedInstance = savedInstanceState
                translucentStatusBar = true

                profile("Joan Heredia", "watxaut@gmail.com") {
                    icon = R.drawable.img
                    identifier = 100
                }
            }


//            primaryItem("Drawer item types") {
//                iicon = GoogleMaterial.Icon.gmd_cloud
//                onClick(openActivity(DrawerItemTypesActivity::class))
//            }
//            primaryItem("Account header options") {
//                iicon = MaterialDesignIconic.Icon.gmi_account
//                onClick(openActivity(AccountHeaderActivity::class))
//            }
//            primaryItem("Header and footer") {
//                iicon = GoogleMaterial.Icon.gmd_menu
//                onClick(openActivity(HeaderFooterActivity::class))
//            }
//            primaryItem("Listeners") {
//                iicon = MaterialDesignIconic.Icon.gmi_audio
//                onClick(openActivity(ListenersActivity::class))
//            }
//            primaryItem("Badges") {
//                iicon = MaterialDesignIconic.Icon.gmi_tag
//                onClick(openActivity(BadgesActivity::class))
//            }

            primaryItem("Home") {}
            divider {}
            primaryItem("Users") {}
            secondaryItem("Settings") {}

            footer {
                primaryItem("Primary item")
                secondaryItem("Secondary item")
            }

        }
    }
}
