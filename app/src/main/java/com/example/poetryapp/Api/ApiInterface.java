package com.example.poetryapp.Api;

import com.example.poetryapp.response.deleteResponse;
import com.example.poetryapp.response.getResponse;
import com.example.poetryapp.response.poetryInsertResponse;
import com.example.poetryapp.response.updatePoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("read.php")
    Call<getResponse> getPoetry();

    @FormUrlEncoded
    @POST("delete.php")
    Call<deleteResponse> deletePoetry(@Field("poetry_id") String poetry_id);

    @FormUrlEncoded
    @POST("index.php")
    Call<poetryInsertResponse> insertPoetry(@Field("poetry") String poetry,@Field("poet_name") String poet_name);

    @FormUrlEncoded
    @POST("update.php")
    Call<updatePoetryResponse> updatePoetry(@Field("poetry_data") String poetry,@Field("id") String id);



}
