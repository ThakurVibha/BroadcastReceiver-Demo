package com.example.fcmdemo.maps.demomaps.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.fcmdemo.R
import com.example.fcmdemo.maps.demomaps.activities.model.GoogleMapModel
import com.example.fcmdemo.maps.demomaps.activities.utils.GoogleMapInterface
import com.example.fcmdemo.maps.demomaps.util.MapsHelper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@Suppress("DEPRECATION")
class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnMapClickListener {
    private val PATTERN_GAP_LENGTH_PX = 20
    private val DOT: PatternItem = Dot()
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    private val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)
    private lateinit var map: GoogleMap
    var mapFragment: SupportMapFragment? = null
    var latLongOrigin: LatLng? = null
    var latLongDestination: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var TAG = "//"
    lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    private lateinit var lastLocation: Location
    var isSource = true
    private var grayPolyline: Polyline? = null
    private var blackPolyline: Polyline? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
        private const val BASE_URL = "https://api.openrouteservice.org/v2/directions/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        makeLocationCall()
        createLocationRequest()
        onClick()
    }

    fun setPolyine(map: GoogleMap) {
        val polyline1 = map.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    //28.5355° N, 77.3910° E
                    latLongOrigin,
                    latLongDestination
                )
        )
        polyline1.tag = "A"
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.644800, 77.216721), 4f))
    }


    private fun drawRoutes() {
        var retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        var googleMapInterface: GoogleMapInterface = retrofit.create(GoogleMapInterface::class.java)
        latLongOrigin?.let {
            googleMapInterface.getRoutes(
                "${it.longitude},${it.latitude}",
                "${latLongDestination!!.longitude},${latLongDestination!!.latitude}"
            )
                .enqueue(object : Callback<GoogleMapModel> {
                    override fun onResponse(
                        call: Call<GoogleMapModel>,
                        response: Response<GoogleMapModel>
                    ) {
                        val coordinates = response.body()!!.features[0].geometry.coordinates
                        MapsHelper.drawPolygonApi(coordinates, mMap = map)
                    }
                    override fun onFailure(call: Call<GoogleMapModel>, t: Throwable) {
                        Log.e(TAG, t.localizedMessage)
                    }
                })
        }
    }
    private fun onClick() {
        isSource = false
        floatingActionButton.setOnClickListener {
            if (isSource) {
                searchLocation()
                setPolyine(map)
            } else {
                searchLocation()
                searchDestination()
            }
        }
    }

    fun searchLocation() {
        isSource = false
        val locationSource: AutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.tvSource)
        lateinit var location: String
        location = locationSource.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext, "provide location", Toast.LENGTH_SHORT).show()
        } else {
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            latLongOrigin = (LatLng(address.latitude.toDouble(), address.longitude.toDouble()))
            Log.e("hi", latLongOrigin.toString())
            map!!.addMarker(MarkerOptions().position(latLng).title(location))
            map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }
    }

    fun searchDestination() {
        isSource = true
        val destinationSource: AutoCompleteTextView =
            findViewById<AutoCompleteTextView>(R.id.tvDestination)
        lateinit var location: String
        location = destinationSource.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext, "provide location", Toast.LENGTH_SHORT).show()
        } else {
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            latLongDestination = (LatLng(address.latitude.toDouble(), address.longitude.toDouble()))
            Log.e("hello", latLongDestination.toString())
            map!!.addMarker(MarkerOptions().position(latLng).title(location))
            map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }

        drawRoutes()
    }

    private fun refreshLocation() {
        map.setOnMapClickListener {
            getLocation.text = MapsHelper.getAddress(this, it)
        }
    }

    //Update lastLocation with new location and update map with new location coordinates.
    private fun makeLocationCall() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                MapsHelper.placeMarkerOnMap(
                    this@GoogleMapsActivity,
                    map,
                    LatLng(lastLocation.latitude, lastLocation.longitude)
                )
            }
        }
    }

    //Receiving Location Updates
    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //2
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    // To Receive Location Updates
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(
                        this,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
        map.isMyLocationEnabled = true
        map.getUiSettings().setZoomControlsEnabled(true)

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
            setUpMap()
        }
        //Adding a Polyline
        MapsHelper.setPolyine(map)
        map.setOnPolylineClickListener(this)
        refreshLocation()
    }

    //granting permissions
    @SuppressLint("MissingPermission")
    private fun setUpMap() {
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        // To add marker at current location
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                MapsHelper.placeMarkerOnMap(
                    this,
                    map,
                    LatLng(lastLocation.latitude, lastLocation.longitude)
                )
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    //to handle location updates
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    //onClick drop the marker
    override fun onPolylineClick(p0: Polyline) {
        if (p0.pattern == null || !p0.pattern!!.contains(DOT)) {
            p0.pattern = PATTERN_POLYLINE_DOTTED
        } else {
            p0.pattern = null
        }
        Toast.makeText(
            this, "Route type " + p0.tag.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onMapClick(p0: LatLng) {
        getLocation.text = MapsHelper.getAddress(this, p0)

    }

}


