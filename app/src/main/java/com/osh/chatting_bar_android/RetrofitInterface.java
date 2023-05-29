package com.osh.chatting_bar_android;

import com.osh.chatting_bar_android.data_model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/auth/sign-up")
    Call<SignUpResponse> sign_up(@Body SignUpRequest signUpRequest);
}
