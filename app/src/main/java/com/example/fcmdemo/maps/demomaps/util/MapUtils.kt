package com.example.fcmdemo.maps.demomaps.util

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.example.fcmdemo.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
object MapUtils {

    fun getLocations():ArrayList<LatLng>{
        var locationList=ArrayList<LatLng>()
        locationList.add(LatLng(28.436970000000002, 77.11272000000001))
        locationList.add(LatLng(28.43635, 77.11289000000001))
        locationList.add(LatLng(28.4353, 77.11317000000001))
        locationList.add(LatLng(28.435280000000002, 77.11332))
        locationList.add(LatLng(28.435350000000003, 77.11368))
        locationList.add(LatLng(28.4356, 77.11498))
        locationList.add(LatLng(28.435660000000002, 77.11519000000001))
        locationList.add(LatLng(28.43568, 77.11521))
        locationList.add(LatLng(28.436580000000003, 77.11499))
        locationList.add(LatLng(28.436590000000002, 77.11507))
        return locationList
    }
    fun getOriginDestinationMarkerBitmap(): Bitmap {
        val height = 20
        val width = 20
        val bitmap = Bitmap.createBitmap(height, width, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }
    fun getCarBitmap(context: Context): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.car)
        return Bitmap.createScaledBitmap(bitmap, 50, 100, false)
    }


}