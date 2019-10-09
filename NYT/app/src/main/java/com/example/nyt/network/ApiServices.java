package com.example.nyt.network;


import com.example.nyt.models.AllListings.ListingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiServices {

    @GET
    Call<ListingResponse> getListings(
            @Url String url,
            @Query("api-key") String apikey);

}

