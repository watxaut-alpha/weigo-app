package com.watxaut.trasloco_27

import android.content.Intent
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
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
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

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

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

            primaryItem("Home") { icon = R.drawable.baseline_home_black_18dp}
            divider {}
            primaryItem("Ranking") {
                icon = R.drawable.baseline_star_black_18dp
                onClick { _ ->
                    val intent = Intent(this@MainActivity, RankingActivity::class.java)
                    startActivity(intent)
                    false
                }
            }
            primaryItem("Referral") {

                onClick { _ ->
                    MaterialDialog(this@MainActivity).show {
                        title(text = "Share the app")
                        message(text = "Share the app with your friends and get a 5€ discount")
                        positiveButton(R.string.share) { dialog ->
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Weigo is the new way to move around Barcelona! Check it out: http://link.com")
                                type = "text/plain"
                            }
                            startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
                        }

                    }
                    false
                }

                icon = R.drawable.baseline_thumb_up_alt_black_18dp
            }
            secondaryItem("Settings") { icon = R.drawable.baseline_settings_black_18dp }

            footer {
                primaryItem("Log Out") { icon = R.drawable.baseline_eject_black_18dp}
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
        private const val PLACE_PICKER_REQUEST = 3
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(this@MainActivity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }


}
