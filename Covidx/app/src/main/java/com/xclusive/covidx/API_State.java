package com.xclusive.covidx;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public  interface API_State {
    @GET("/data.json")
    Call<State> getStatedata();
}
