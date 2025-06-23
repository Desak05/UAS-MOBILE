package com.example.uas_mobile;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.Call;

public interface ApiService {
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseModel> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> login(
            @Field("username") String username,
            @Field("password") String password
    );
}