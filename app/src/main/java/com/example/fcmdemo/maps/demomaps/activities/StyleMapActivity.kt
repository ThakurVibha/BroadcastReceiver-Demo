package com.example.fcmdemo.maps.demomaps.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fcmdemo.R
import com.example.fcmdemo.maps.demomaps.util.AnimationUtils
import com.example.fcmdemo.maps.demomaps.util.MapUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.transition.MaterialArcMotion
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlinx.android.synthetic.main.activity_style_map.*


class StyleMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var greyPolyLine: Polyline? = null
    private var blackPolyLine: Polyline? = null
    private var originMarker:Marker?=null
    private var destinationMarker:Marker?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style_map)
        val mapFragment2 = supportFragmentManager.findFragmentById(R.id.map2) as? SupportMapFragment
        mapFragment2?.getMapAsync(this)

    }
private fun addOriginDestinationMarkere(latLng: LatLng):Marker{
    val bitmapDescriptor =
        BitmapDescriptorFactory.fromBitmap(MapUtils.getOriginDestinationMarkerBitmap())
    return map.addMarker(MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor))

}

    private fun moveCamera(latLng: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    private fun animateCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(15.5f).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun showDefaultLocation(latLng: LatLng) {
        moveCamera(latLng)
        animateCamera(latLng)
    }

    private fun showPath(latLngList: ArrayList<LatLng>) {
        val builder = LatLngBounds.Builder()
        for (latLng in latLngList) {
            builder.include(latLng)
        }
        // this is used to set the bound of the Map
        val bounds = builder.build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 2))

        //Drawing Polylines
        val greyPolylineOptions = PolylineOptions()
        greyPolylineOptions.color(Color.GRAY)
        greyPolylineOptions.width(5f)
        greyPolylineOptions.addAll(latLngList)
        greyPolyLine = map.addPolyline(greyPolylineOptions)

        val blackPolylineOptions = PolylineOptions()
        blackPolylineOptions.color(Color.BLACK)
        blackPolylineOptions.width(5f)
        blackPolyLine = map.addPolyline(blackPolylineOptions)

        //setting marker
        originMarker=addOriginDestinationMarkere(latLngList[0])
        originMarker?.setAnchor(0.5f, 0.5f)
        destinationMarker = addOriginDestinationMarkere(latLngList[latLngList.size - 1])
        destinationMarker?.setAnchor(0.5f, 0.5f)
        val polyLineAnimator=AnimationUtils.polylineAnimator()
        polyLineAnimator.addUpdateListener {
            val percentValue = (it.animatedValue as Int)
            val index = (greyPolyLine?.points!!.size) * (percentValue / 100.0f).toInt()
            blackPolyLine?.points = greyPolyLine?.points!!.subList(0, index)
        }
        polyLineAnimator.start()
    }

    override fun onMapReady(map2: GoogleMap) {
//        boundRegion(map2)
        this.map = map2
        val defaultLocation = LatLng(40.7128, 74.0060)
        showDefaultLocation(defaultLocation)
        map2.getUiSettings().setZoomGesturesEnabled(false);

    }

    //    private fun boundRegion(map2: GoogleMap) {
//        //To bound specific region
//        var one = LatLng(30.7333, 76.7794)
//        var two = LatLng(30.6425, 76.8173)
//        val builder = LatLngBounds.Builder()
//        builder.include(one)
//        builder.include(two)
//        builder.build().center
//        map2?.uiSettings?.isMyLocationButtonEnabled = true
//        val bounds = builder.build()
//        val width = resources.displayMetrics.widthPixels
//        val height = resources.displayMetrics.heightPixels
//        val padding = (width * 0.10).toInt()
//        map2.setLatLngBoundsForCameraTarget(bounds);
//        map2.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
//        map2.getUiSettings().setZoomGesturesEnabled(false);
//    }

}