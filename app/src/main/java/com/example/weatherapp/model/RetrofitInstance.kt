package com.example.weatherapp.model

import ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getWeather(apikey:String,city:String,onError:(String)->Unit={},onSuccess:(WeatherResponse)->Unit={}){
        try {
            val response = apiService.getWeather(apikey,city)
        response.enqueue(object : Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        onSuccess(response.body()!!)
                        onError("no error")
                    }
                    else{
                        onError("body is null")
                    }
                }
                else{
                    onError("is not Successful")
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        })

         }
       catch (e:java.net.SocketException){
            onError("socket exception")
        }
        catch (e:java.lang.Exception){
            onError("exception:${e}")
        }
    }
}