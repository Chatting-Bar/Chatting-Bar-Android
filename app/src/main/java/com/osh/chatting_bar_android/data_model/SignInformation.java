package com.osh.chatting_bar_android.data_model;

import com.google.gson.annotations.SerializedName;

public class SignInformation {
    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    @SerializedName("refreshToken")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    @SerializedName("message")
    private String messeage;

    public String getMesseage() {
        return messeage;
    }


    @Override
    public String toString()
    {
        return "accessToken: " + accessToken + "\n refreshToken: " + refreshToken + "\n" + messeage;
    }
}
