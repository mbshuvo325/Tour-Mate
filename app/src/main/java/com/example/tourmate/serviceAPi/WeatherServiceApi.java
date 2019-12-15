package com.example.tourmate.serviceAPi;

import com.example.tourmate.currentWeather.CurrentWeatherResponsebody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherServiceApi {
    @GET
    Call<CurrentWeatherResponsebody> getCurrentWeather(@Url String endUrl);
}