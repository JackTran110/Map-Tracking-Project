package com.example.nasaearthyimage.DataModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    String BASE_URL = "https://api.nasa.gov/";

    @GET("planetary/apod")
    Call<NASAModelData> getNasaData(@Query("api_key") String api_key,
                                    @Query("date") String date);
}
