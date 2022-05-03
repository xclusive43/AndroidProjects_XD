package com.xclusive.covidx;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_stateinstance {
    private static Retrofit retrofit;
    private static final String BASEURL = "https://data.covid19india.org/";

    public static Retrofit getRetrofitstateinstance(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
