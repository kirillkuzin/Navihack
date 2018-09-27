package hack.naviaddress.kirillkuzin.maraudersmap

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut

class Core {

    var map : Map? = null

    init {
        FuelManager.instance.basePath = "http://maraudersmap.us-east-2.elasticbeanstalk.com/api"
    }

    fun getAddresses() {
        "/getAddresses".httpGet().responseJson { request, response, result ->
            val (data, error) = result
            if (error == null) {
                val jsonArray = data!!.array()
                for (i in 0..(jsonArray.length() - 1)) {
                    val address = Klaxon().parse<Address>(jsonArray[i].toString())
                    map!!.createMarker(address!!.point.lat, address!!.point.lng, address!!.name)
                }
            } else {
                println(error)
            }
        }
    }

    fun updateAddresses() {
        "/getAddresses".httpGet().responseJson { request, response, result ->
            val (data, error) = result
            if (error == null) {
                val jsonArray = data!!.array()
                for (i in 0..(jsonArray.length() - 1)) {
                    val address = Klaxon().parse<Address>(jsonArray[i].toString())
                    map!!.updateMarker(address!!.point.lat, address!!.point.lng, address!!.name)
                }
            } else {
                println(error)
            }
        }
    }

    fun updateMyAddress(_latitude : Double, _longitude : Double, _title : String) {
        "/updateAddress".httpPut(listOf("latitude" to _latitude, "longitude" to _longitude, "title" to _title)).responseString { request, response, result ->
        }
    }
}