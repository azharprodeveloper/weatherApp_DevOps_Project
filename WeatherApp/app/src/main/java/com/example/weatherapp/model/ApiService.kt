import com.example.weather.model.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getWeather(
        @Query("appid") appid: String?,
        @Query("q") q: String?
    ): Call<WeatherResponse>
}