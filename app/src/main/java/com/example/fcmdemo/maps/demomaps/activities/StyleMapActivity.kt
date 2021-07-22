package com.example.fcmdemo.maps.demomaps.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fcmdemo.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class StyleMapActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style_map)
        val mapFragment2 = supportFragmentManager.findFragmentById(R.id.map2) as? SupportMapFragment
        mapFragment2?.getMapAsync(this)
    }
    override fun onMapReady(map2: GoogleMap) {
        //To bound specific region
        var one = LatLng(30.7333, 76.7794)
        var two = LatLng(30.6425, 76.8173)
        val builder = LatLngBounds.Builder()
        builder.include(one)
        builder.include(two)
        builder.build().center
        map2?.uiSettings?.isMyLocationButtonEnabled = true
        val bounds = builder.build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.10).toInt()
        map2.setLatLngBoundsForCameraTarget(bounds);
        map2.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
        map2.getUiSettings().setZoomGesturesEnabled(false);


    }
}