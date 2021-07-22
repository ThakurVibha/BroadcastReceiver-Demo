package com.example.fcmdemo.maps.demomaps.Helper

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

object MapsHelper {
    //To get address of current location onMarkerClick
    fun getAddress(context: Context,location: LatLng): String {
        // 1
        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                val i = 0
//                    addressText += if (i == 1) address.getAddressLine(i) else "\n" + address.getAddressLine(
//                        i
//                    )
                addressText += address.getAddressLine(i)
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return addressText
    }
    fun addMarker(mMap: GoogleMap, latlong: LatLng, animate: Boolean, context: Context) : Marker{

        val addMarker = mMap.addMarker(MarkerOptions().position(latlong))
        if (animate){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latlong.latitude,
                latlong.longitude),
                mMap.cameraPosition.zoom))
            val markerOptions=MarkerOptions().position(latlong)
            val titleStr = MapsHelper.getAddress(context,latlong)
            markerOptions.title(titleStr)
            mMap.addMarker(markerOptions)
        }
        return addMarker
    }
    fun addMarkerWithAdapter(mMap: GoogleMap, latlong: LatLng, animate: Boolean, context: Context) : Marker{

            val addMarker = mMap.addMarker(MarkerOptions().position(latlong))
        if (animate){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latlong.latitude,
                latlong.longitude),
                mMap.cameraPosition.zoom))
        }
        return addMarker
    }
    //placing marker
    fun placeMarkerOnMap(context: Context, mMap: GoogleMap, location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        val titleStr = MapsHelper.getAddress(context,location)  // add these two lines
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions)
    }
}