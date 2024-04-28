package com.example.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.model.RetrofitInstance
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.check.setOnClickListener {
            if(binding.nameText.text.isNotEmpty()){
                getWeather()
            }
            else{
                Toast.makeText(this, "Please enter city name", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getWeather() {
        CoroutineScope(IO).launch{
            RetrofitInstance.getWeather("703171f6206233f62f0060163f7a2c83",binding.nameText.text.toString(), onError = {}, onSuccess = {
                CoroutineScope(Main).launch {
                    with(binding){
                        it.name.also { cityTextView.text = it }
                        "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(it.dt.toLong() * 1000)).also { updatedTextView.text = it }
                        it.weather[0].description.also { cloudTextview.text = it }
                        "${it.main.temp} °C".also { tempTextView.text = it }
                        "${it.main.temp_min} °C".also { minTempTextView.text = it }
                        "${it.main.temp_max} °C".also { maxTempTextView.text = it }
                        it.sys.sunrise.toString().also { sunriseTextView.text = it }
                        it.sys.sunset.toString().also { sunsetTextVIew.text = it }
                        it.wind.speed.toString().also { windTextView.text = it }
                        it.main.pressure.toString().also { pressureTextView.text = it }
                        it.main.humidity.toString().also { humidityTextView.text = it }
                    }
                }
            })
        }
    }
}