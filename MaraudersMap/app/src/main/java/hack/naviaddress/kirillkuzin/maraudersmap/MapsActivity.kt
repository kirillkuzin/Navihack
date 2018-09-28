package hack.naviaddress.kirillkuzin.maraudersmap

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var map = Map()
    private var core = Core()
    private var locationManager : LocationManager? = null
    private var login : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions,0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        login = getIntent().getExtras().getString("login")
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map.mMap = googleMap
        map.start()
        map.mMap!!.isMyLocationEnabled = true
        core.map = map
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(_location: Location) {
            Log.d("LOCATION", _location.longitude.toString() + ":" + _location.latitude.toString())
            core.updateMyAddress(_location.latitude, _location.longitude, login)
            core.updateAddresses()
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
