package hack.naviaddress.kirillkuzin.maraudersmap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.Button
import android.widget.TextView

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import hack.naviaddress.kirillkuzin.maraudersmap.R.id.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FuelManager.instance.basePath = "http://maraudersmap.us-east-2.elasticbeanstalk.com/api"
        val button = findViewById<Button>(button)
        button.setOnClickListener { auth() }
    }

    fun auth() {
        val inputLogin = findViewById<TextInputEditText>(inputLogin)
        val inputPassword = findViewById<TextInputEditText>(inputPassword)
        val wrongView = findViewById<TextView>(wrongView)
        val login = inputLogin.text.toString()
        val password = inputPassword.text.toString()
        if (login == "" || password == "") {
            wrongView.setText("Поля не должны быть пустыми")
            return
        } else {
            "/auth".httpPost(listOf("login" to login, "password" to password)).responseString { request, response, result ->
                val (data, error) = result
                if (error == null) {
                    if (data == "True") {
                        val intent = Intent(this, MapsActivity::class.java)
                        intent.putExtra("login", login)
                        startActivity(intent)
                    } else {
                        wrongView.setText("Неверный пароль")
                    }
                } else {
                    println(error)
                }
            }
        }
    }
}
