package com.xclusive.covidx;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utilsclass {
    private static Retrofit retrofit = null,retrofit1 = null;

    public static Api api() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Api.baseusl).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit.create(Api.class);
    }

}
