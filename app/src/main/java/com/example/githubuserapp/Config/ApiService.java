package com.example.githubuserapp.Config;

import com.example.githubuserapp.Models.DetailResponse;
import com.example.githubuserapp.Models.GitHubResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/users")
    @Headers("Authorization: Bearer " + TOKEN)
    Call<GitHubResponse> getUsers(@Query("q") String username);

    @GET("users/{username}")
    Call<DetailResponse> getDetailUser(@Path("username") String username);

    String TOKEN = "";
}