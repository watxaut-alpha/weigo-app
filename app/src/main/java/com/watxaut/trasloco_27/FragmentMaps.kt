package com.watxaut.trasloco_27

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import androidx.fragment.app.Fragment
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException


class FragmentMaps : SupportMapFragment() {

    private lateinit var mMapView: MapView
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null

    private val ecooltra_arr = arrayOf(
        arrayOf(41.400035, 2.191791),
        arrayOf(41.400880, 2.190275),
        arrayOf(41.403664, 2.189234),
        arrayOf(41.406151, 2.185972),
        arrayOf(41.408750, 2.180200),
        arrayOf(41.400217, 2.177633),
        arrayOf(41.394545, 2.171522),
        arrayOf(41.396026, 2.178609),
        arrayOf(41.407726, 2.194981),
        arrayOf(41.410365, 2.190335),
        arrayOf(41.413503, 2.177152)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        mMapView = rootView.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)

        mMapView.onResume() // needed to get the map to display immediately

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync { mMap ->
            googleMap = mMap
            googleMap!!.uiSettings.setAllGesturesEnabled(true)
            googleMap!!.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener {marker: Marker? -> false })

            val demium = LatLng(41.399376, 2.192357)  //Demium
            var initLoc: LatLng

            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                initLoc = LatLng(41.403495, 2.174360)  //SaFa
            } else {
                // For showing a move to my location button

                googleMap!!.isMyLocationEnabled = true
                initLoc = LatLng(41.403495, 2.174360)
                // 2
                fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
                    // Got last known location. In some rare situations this can be null.
                    // 3
                    if (location != null) {
                        lastLocation = location
                        initLoc = LatLng(location.latitude, location.longitude)
                        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(initLoc, 16f))
                    }
                }
            }

            // Add ecooltras to map
            for (arr in ecooltra_arr) {
                googleMap!!.addMarker(MarkerOptions().position(LatLng(arr[0], arr[1]))
                    .title("ecooltra")
                    .snippet("Moto")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ecooltra)))

            }

            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(initLoc, 16.0f))

//            // For zooming automatically to the location of the marker
//            val cameraPosition = CameraPosition.Builder().target(demium).zoom(12f).build()
//            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }








        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            loadPlacePicker()
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(activity), FragmentMaps.PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val PLACE_PICKER_REQUEST = 3
    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(activity)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)

        googleMap!!.addMarker(markerOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FragmentMaps.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(activity, data)
                var addressText = place.name.toString()
                addressText += "\n" + place.address.toString()

                placeMarkerOnMap(place.latLng)
                googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 16f))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}
