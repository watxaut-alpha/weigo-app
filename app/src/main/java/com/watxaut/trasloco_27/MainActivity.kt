package com.watxaut.trasloco_27

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
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

class MainActivity : AppCompatActivity()  {

    private lateinit var result: Drawer
    private lateinit var headerResult: AccountHeader


    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {

                    val fragment = FragmentHome.Companion.newInstance()
                    addFragment(fragment)

                    return true
                }
                R.id.navigation_maps -> {
                    val fragment = FragmentMaps()
                    addFragment(fragment)
                    return true
                }
                R.id.navigation_tickets -> {
                    var fragment = FragmentTickets()
                    addFragment(fragment)
                    return true
                }
            }
            return false
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.contentfragment, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = findViewById<FrameLayout>(R.id.contentfragment)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        val mapFragment =
//            supportFragmentManager.findFragmentById(R.id.map) as FragmentMaps
//        /*mapFragment.initial_latitude = -10.0
//        mapFragment.initial_longitude = 115.0
//        mapFragment.initial_marker = "Inishol mawker"*/
//        mapFragment.getMapAsync(mapFragment)
//        System.err.println("OnCreate end")

        val fragment = FragmentHome.Companion.newInstance()
        addFragment(fragment)

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

            primaryItem("Home") {}
            divider {}
            primaryItem("Users") {}
            primaryItem("Referral") {}
            secondaryItem("Settings") {}

            footer {
                primaryItem("Primary item")
                secondaryItem("Log Out")
            }

        }

        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}
