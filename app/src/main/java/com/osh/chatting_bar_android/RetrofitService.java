package com.osh.chatting_bar_android;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.osh.chatting_bar_android.data_model.BaseResponse;
import com.osh.chatting_bar_android.data_model.SignInResponse;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String BASE_URL = "http://www.chatbar.kro.kr:8080";

    public static RetrofitInterface getApiService(){return getToken().create(RetrofitInterface.class);}

//    public static RetrofitInterface getTokenService(){return getToken().create(RetrofitInterface.class);} //토큰 새로받기위한 retrofit

    private static Retrofit getToken(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().header("Content-Type", "application/json").build();
                                //.header("accessToken", "Bearer " + User.getInstance().getPreferences().getString("AccessToken", "")).build();
                        Response response = chain.proceed(request);
                        if (response.code() == 401) {
                            response.close();
                            Log.d("test", "재발급 이전\n" + User.getInstance().getPreferences().getString("AccessToken", "") + "\n" +
                                    User.getInstance().getPreferences().getString("RefreshToken", ""));
                            SignInResponse tokenResponse = getApiService().refresh(new stringRequest(User.getInstance().getPreferences().getString("RefreshToken", ""))).execute().body();
                            String token = tokenResponse.getInformation().getAccessToken();
                            String Rtoken = tokenResponse.getInformation().getRefreshToken();
                            User.getInstance().getPreferences().edit().putString("AccessToken", token).apply();
                            User.getInstance().getPreferences().edit().putString("RefreshToken", Rtoken).apply();
                            Log.d("test", "재발급 이후\n" + User.getInstance().getPreferences().getString("AccessToken", "") + "\n" +
                                    User.getInstance().getPreferences().getString("RefreshToken", ""));
                            //Request newRequest = chain.request().newBuilder().addHeader("accessToken", token).build();
                            Response newResponse = chain.proceed(request);
                            Log.d("test", "intercept: " + newResponse.code());
                            return newResponse;
                        }
                        return response;
                    }
                }).build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

//        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
}
