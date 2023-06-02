package com.osh.chatting_bar_android.data_model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class ChatRoomInfomation {
    @SerializedName("id")
    private Long id;

    public Long getId() {
        return id;
    }

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    @SerializedName("hostName")
    private String hostName;

    public String getHostName() {
        return hostName;
    }

    @SerializedName("participant")
    private String participant;

    public String getParticipant() {
        return participant;
    }

    @SerializedName("time")
    private String time;

    public String getTime() {
        return time;
    }

    @SerializedName("categories")
    private Set<Categories> categories;

    public Set<Categories> getCategories() {
        return categories;
    }


    @Override
    public String toString()
    {
        return "id: " + id + "\nname: " + name + "\nhostName: " + hostName + "\nparticipant" + participant + "\ntime" + time + "\ncategories" + categories;
    }
}
