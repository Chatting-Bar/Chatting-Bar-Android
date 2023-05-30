package com.osh.chatting_bar_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.MyViewHolder>{

    private List<String> hostList;
    private Context context;
    public ChatRoomRecyclerViewAdapter(Context context, List<String>  hostList) {

        this.hostList = hostList;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatRoomRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomRecyclerViewAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chatroom_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ChatRoomRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(hostList.get(position));
    }

    @Override
    public int getItemCount() {
        return hostList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.hostNickname_text);
        }
    }
}
