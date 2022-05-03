package com.xclusive.covidx;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface Api {
    static final String baseusl = "https://corona.lmao.ninja/v2/";
    @Headers("Content-Type: application/json")
    @GET("countries")
    Call<List<Data>> getcountrydata();


}
