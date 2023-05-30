package com.osh.chatting_bar_android;

import com.osh.chatting_bar_android.data_model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @Headers("Content-Type: application/json")
    @POST("/auth/sign-up")
    Call<BaseResponse> sign_up(@Body SignUpRequest signUpRequest);

    @Headers("Content-Type: application/json")
    @POST("/auth/sign-in")
    Call<SignInResponse> sign_in(@Body SignInRequest signInRequest);

    @Headers("Content-Type: application/json")
    @POST("/auth/sign-out")
    Call<BaseResponse> sign_out(@Field("refreshToken") String refreshToken);
}
