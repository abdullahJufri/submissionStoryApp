package com.bangkit.submissionstoryapp.ui.maps

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.databinding.ActivityMapsBinding
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.main.MainViewmodels
import com.bangkit.submissionstoryapp.ui.main.dataStore
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewmodels
    private lateinit var authentication: Authentication

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreference.getInstance(dataStore)

        mapsViewModel =
            ViewModelProvider(this, ViewModelFactory(pref, this))[MapsViewmodels::class.java]

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

//        mapsViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore), this)
//        )[MapsViewmodels::class.java]
//
        mapsViewModel.getUser().observe(this) {
            authentication = Authentication(
                it.name,
                it.email,
                it.password,
                it.userId,
                it.token,
                true
            )
            mapsViewModel.getLocation(it.token)

        }
//        mapsViewModel.getLocation(authentication.token)

//        mapsViewModel.getToken().observe(
//            this
//        ) { token: String ->
//            if (token.isNotEmpty()) {
//                mapsViewModel.getLocation(token)
//            }
//        }

        mapsViewModel.listStory.observe(this) {
            for (story in it) {
                if (story.lat != null && story.lon != null) {
                    val position = LatLng(story.lat, story.lon)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title(story.name)
                            .snippet(story.description)
                    )
                }
            }
            val firstLocation = LatLng(it[0].lat as Double, it[0].lon as Double)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 15f))
        }

        getMyLocation()
        setMapStyle()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(ContentValues.TAG, "Style Parsing Failed.")
            }
        } catch (exeception: Resources.NotFoundException) {
            Log.e(ContentValues.TAG, "Can't Find Style. Error: ", exeception)
        }
    }
}