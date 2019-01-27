package com.watxaut.trasloco_27

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tickets.view.*
import androidx.recyclerview.widget.LinearLayoutManager




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


        return rootView
    }

}
