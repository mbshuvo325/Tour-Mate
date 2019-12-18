package com.example.tourmate.serviceAPi;

import com.example.tourmate.nearbyPlace.NearbyResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NearByserviceApi {

    @GET
    Call<NearbyResponseBody> getNearByResponse(@Url String endUrl);
}
