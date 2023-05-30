package com.osh.chatting_bar_android.data_model;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("check")
    private boolean check;

    @SerializedName("information")
    private SignUpInformation information;

    public boolean getCheck() {
        return check;
    }

    public SignUpInformation getInformation() {
        return information;
    }

    @Override
    public String toString()
    {
        return "체크 : " + check + ", infomation: " + information.toString();
    }
}

class SignUpInformation {
    @SerializedName("message")
    private String messeage;

    public String getMesseage() {
        return messeage;
    }

    @Override
    public String toString()
    {
        return messeage;
    }
}
