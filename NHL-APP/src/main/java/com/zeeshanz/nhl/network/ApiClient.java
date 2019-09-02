package com.zeeshanz.nhl.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL_NHL = "https://statsapi.web.nhl.com/api/v1/";
    private static Retrofit retrofitNhl = null;

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    public static Retrofit getNHLClient() {
        if (retrofitNhl == null) {
            retrofitNhl = new Retrofit.Builder()
                    .baseUrl(BASE_URL_NHL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitNhl;
    }
}
