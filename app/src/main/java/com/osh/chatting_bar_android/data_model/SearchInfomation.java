package com.osh.chatting_bar_android.data_model;

import com.google.gson.annotations.SerializedName;

public class SearchInfomation {
    @SerializedName("searchWord")
    private String searchWord;

    public String getSearchWord() {
        return searchWord;
    }

    @SerializedName("information")
    private ChatRoomInfomation information;

    public ChatRoomInfomation getInformation() {
        return information;
    }

    @Override
    public String toString()
    {
        return "검색어: " + searchWord + "information: " + information;
    }
}
