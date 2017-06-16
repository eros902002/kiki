package com.erostech.kiki.api;

import com.erostech.kiki.ConstantsKt;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by erosgarciaponte on 16.06.17.
 */

public class CountryService {
    private void createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsKt.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        CountryApi api = retrofit.create(CountryApi.class);
    }
}
