package hack.naviaddress.kirillkuzin.maraudersmap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Map {

    var mMap : GoogleMap? = null
    val addresses = mutableMapOf<String, Marker>()

    fun start() {
        val moscow = LatLng(55.7525154, 37.6230937)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(moscow))
//        mMap!!.moveCamera(CameraUpdateFactory.zoomTo(10.0f))
    }

    fun updateMarker(_latitude : Double, _longitude : Double, _title : String) {
        val position = LatLng(_latitude, _longitude)
        val marker : Marker? = addresses[_title]
        if (marker == null) {
            createMarker(_latitude, _longitude, _title)
        } else {
            marker!!.position = position
        }
    }

    fun createMarker(_latitude : Double, _longitude : Double, _title : String) {
        val position = LatLng(_latitude, _longitude)
        val marker = mMap!!.addMarker(MarkerOptions().position(position).title(_title).icon(BitmapDescriptorFactory.fromAsset("marker.png")))
        addresses[_title] = marker
    }

}