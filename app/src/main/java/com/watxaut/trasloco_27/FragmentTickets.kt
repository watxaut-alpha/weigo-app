package com.watxaut.trasloco_27

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tickets.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import android.app.Dialog
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import android.net.Uri
import android.util.Log


class FragmentTickets : Fragment() {

    companion object {
        fun newInstance(): FragmentTickets {
            var fragmentHome = FragmentTickets()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.card_targeta, container, false)

        var card_t10 = rootView!!.findViewById(R.id.cardt10) as CardView
        var card_tjove = rootView.findViewById(R.id.cardtjove) as CardView
        var card_fgc = rootView.findViewById(R.id.cardfgc) as CardView
        var fab_buy = rootView.findViewById(R.id.fabbuy) as FloatingActionButton

        card_t10.setOnClickListener {
            val settingsDialog = Dialog(context!!)
            settingsDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            settingsDialog.setContentView(layoutInflater.inflate(R.layout.img_layout, null))
            settingsDialog.show()
        }

        card_tjove.setOnClickListener {
            val settingsDialog = Dialog(context!!)
            settingsDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            settingsDialog.setContentView(layoutInflater.inflate(R.layout.img_layout, null))
            settingsDialog.show()
        }

        card_fgc.setOnClickListener {
            val settingsDialog = Dialog(context!!)
            settingsDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            settingsDialog.setContentView(layoutInflater.inflate(R.layout.img_layout, null))
            settingsDialog.show()
        }

        fab_buy.setOnClickListener {

            val launchIntent = activity!!.packageManager.getLaunchIntentForPackage("com.geomobile.tmbmobile")
            if (launchIntent != null) {
                startActivity(launchIntent)//null pointer check in case package name was not found
            }else{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.geomobile.tmbmobile")))
                Log.i("FRAGMENTTICKET", "App not installed, opening Market")
            }
        }



        return rootView
    }

}
