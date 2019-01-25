package com.watxaut.trasloco_27

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class FragmentMaps : SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    override fun onMarkerClick(p0: Marker?) = false

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    var initial_latitude  = -34.0
    var initial_longitude = 151.0
    var initial_marker    = "Seed nay"
    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        attrs ?: return
        context ?: return

        val typed_array = context.obtainStyledAttributes(attrs,
            R.styleable.MyyMap, 0, 0)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        try {
            val provided_latitude =
                typed_array.getFloat(R.styleable.MyyMap_latitude, initial_latitude.toFloat())
            initial_latitude  = provided_latitude.toDouble()

            val provided_longitude =
                typed_array.getFloat(R.styleable.MyyMap_longitude, initial_longitude.toFloat())
            initial_longitude = provided_longitude.toDouble()

            val provided_marker =
                typed_array.getString(R.styleable.MyyMap_marker)
            provided_marker?.apply { initial_marker = provided_marker }

        }
        catch(e: Exception) {

        }

        typed_array.recycle()

    }
    override fun onMapReady(map: GoogleMap?) {
        System.err.println("OnMapReady start")
        mMap = map as GoogleMap

        val sydney = LatLng(initial_latitude, initial_longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title(initial_marker))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        Toast.makeText(this.context, "OnMapReady end", Toast.LENGTH_LONG).show()
    }


}


