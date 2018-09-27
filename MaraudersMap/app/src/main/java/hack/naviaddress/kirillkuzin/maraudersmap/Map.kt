package hack.naviaddress.kirillkuzin.maraudersmap

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Map {

    var mMap : GoogleMap? = null
    val addresses = mutableMapOf<String, Marker>()

    fun createMarker(_latitude : Double, _longitude : Double, _title : String) {
        val position = LatLng(_latitude, _longitude)
        val marker = mMap!!.addMarker(MarkerOptions().position(position).title(_title))
        addresses[_title] = marker
    }

    fun updateMarker(_latitude : Double, _longitude : Double, _title : String) {
        val position = LatLng(_latitude, _longitude)
        val marker : Marker? = addresses[_title]
        marker!!.position = position
    }

}