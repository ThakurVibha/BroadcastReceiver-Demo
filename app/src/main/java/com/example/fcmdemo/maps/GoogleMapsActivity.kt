package com.example.fcmdemo.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fcmdemo.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import kotlinx.android.synthetic.main.activity_google_maps.*
import java.lang.Exception

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var mapFragment: SupportMapFragment? = null
    var locationPermissionGranted = false
    var isMyLocationEnabled = true
    var placesClient = Places.createClient(this)
    var lastKnownLocation = "Sysdney"
    var defaultLocation = "India"
    var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    var TAG = "//"


    val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            it.forEach{ permission ->
            Log.e(TAG, ": "+permission)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        //construct a place client
        Places.initialize(applicationContext, getString(R.string.maps_api_key))
        // Construct a FusedLocationProviderClient.

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        getLocationPermission()

        requestmyPermission()

    }

    private fun requestmyPermission() {
        registerForActivityResult.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION))
    }

    // runtime permisssions
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(30.63312134134044, 76.8246376336528)
//        mMap.addMarker(
//            MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney")
//        )
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        updateLocationUI()
        getDeviceLocation()
    }

    private fun getDeviceLocation() {
/*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->

                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun updateLocationUI() {
        if (map == null)
            return
        try {


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.option_get_place) {
//            showCurrentPlace()
//        }
//        return true
//    }

//    @SuppressLint("MissingPermission")
//    private fun showCurrentPlace() {
//        if (map == null) {
//            return
//        }
//        if (locationPermissionGranted) {
//            // Use fields to define the data types to return.
//            val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//
//            // Use the builder to create a FindCurrentPlaceRequest.
//            val request = FindCurrentPlaceRequest.newInstance(placeFields)
//
//            // Get the likely places - that is, the businesses and other points of interest that
//            // are the best match for the device's current location.
//            val placeResult = placesClient.findCurrentPlace(request)
//            placeResult.addOnCompleteListener { task ->
//                if (task.isSuccessful && task.result != null) {
//                    val likelyPlaces = task.result
//
//                    // Set the count, handling cases where less than 5 entries are returned.
//                    val count = if (likelyPlaces != null && likelyPlaces.placeLikelihoods.size < M_MAX_ENTRIES) {
//                        likelyPlaces.placeLikelihoods.size
//                    } else {
//                        M_MAX_ENTRIES
//                    }
//                    var i = 0
//                    likelyPlaceNames = arrayOfNulls(count)
//                    likelyPlaceAddresses = arrayOfNulls(count)
//                    likelyPlaceAttributions = arrayOfNulls<List<*>?>(count)
//                    likelyPlaceLatLngs = arrayOfNulls(count)
//                    for (placeLikelihood in likelyPlaces?.placeLikelihoods ?: emptyList()) {
//                        // Build a list of likely places to show the user.
//                        likelyPlaceNames[i] = placeLikelihood.place.name
//                        likelyPlaceAddresses[i] = placeLikelihood.place.address
//                        likelyPlaceAttributions[i] = placeLikelihood.place.attributions
//                        likelyPlaceLatLngs[i] = placeLikelihood.place.latLng
//                        i++
//                        if (i > count - 1) {
//                            break
//                        }
//                    }
//
//                    // Show a dialog offering the user the list of likely places, and add a
//                    // marker at the selected place.
//                    openPlacesDialog()
//                } else {
//                    Log.e(TAG, "Exception: %s", task.exception)
//                }
//            }
//        } else {
//            // The user has not granted permission.
//            Log.i(TAG, "The user did not grant location permission.")
//
//            // Add a default marker, because the user hasn't selected a place.
//            map?.addMarker(MarkerOptions()
//                .title(getString(R.string.default_info_title))
//                .position(defaultLocation)
//                .snippet(getString(R.string.default_info_snippet)))
//
//            // Prompt the user for permission.
//            getLocationPermission()
//        }
//    }
}
