package com.example.fcmdemo.maps.demomaps.codelabmap.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.fcmdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.fcmdemo.maps.demomaps.codelabmap.model.MapPlace
import com.example.fcmdemo.maps.demomaps.util.BitmapHelper
import com.example.fcmdemo.maps.demomaps.util.MapsHelper
import com.example.fcmdemo.maps.demomaps.util.PlaceRenderer
import com.example.fcmdemo.maps.demomaps.util.PlacesReader
import com.example.myfirstapp.GoogleMaps.MarkerInfoWindowAdapter
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_codelab_map.*

class CodelabMapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mapCodelab: GoogleMap
    private var circle: Circle? = null

    private val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.black)
        BitmapHelper.vectorToBitmap(this, R.drawable.bicycle, color)
    }

    //This code invokes the read() method on a PlacesReader, which returns a List<Place>
    private val places: List<MapPlace> by lazy {
        PlacesReader(this).read()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codelab_map)
        val mapFragment2 =
            supportFragmentManager.findFragmentById(R.id.codelabMap) as? SupportMapFragment
        mapFragment2?.getMapAsync(this)
    }

    override fun onMapReady(mainmap: GoogleMap) {
        mapCodelab = mainmap
        mapCodelab.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
        MapsHelper.addClusteredMarkers(this,places,mainmap)
        places.forEach {
            val markeroptions = MarkerOptions().title(it.name).position(it.latLng).icon(bicycleIcon)
                .snippet(it.address).position(it.latLng)
            mainmap.addMarker(markeroptions)
        }
        addCircle(mapCodelab)
    }
    //Adding a circle
    private fun addCircle(mainmap: GoogleMap) {
        circle = mainmap.addCircle(
            CircleOptions().center(LatLng(30.387672910020356, 76.77570959079574))
                .fillColor(ContextCompat.getColor(this, R.color.black)).radius(
                10000.0
            ).strokeColor(ContextCompat.getColor(this, R.color.teal_700))
        )
    }


}

