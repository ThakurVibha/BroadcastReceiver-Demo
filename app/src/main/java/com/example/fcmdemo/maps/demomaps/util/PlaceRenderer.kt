package com.example.fcmdemo.maps.demomaps.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.fcmdemo.R
import com.example.fcmdemo.maps.demomaps.codelabmap.model.MapPlace
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class PlaceRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MapPlace>
) : DefaultClusterRenderer<MapPlace>(context, map, clusterManager) {
    /**
     * The icon to use for each cluster item
     */
    private val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context,
            R.color.teal_700
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.bicycle,
            color
        )
    }

    /**
     * Method called before the cluster item (the marker) is rendered.
     * This is where marker options should be set.
     */
    override fun onBeforeClusterItemRendered(
        item: MapPlace,
        markerOptions: MarkerOptions
    ) {
        markerOptions.title(item.name)
            .position(item.latLng)
            .icon(bicycleIcon)
    }

    /**
     * Method called right after the cluster item (the marker) is rendered.
     * This is where properties for the Marker object should be set.
     */
    override fun onClusterItemRendered(clusterItem: MapPlace, marker: Marker) {
        marker.tag = clusterItem
    }
}