package com.osh.chatting_bar_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osh.chatting_bar_android.data_model.ChatRoomInfomation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.MyViewHolder>{

    private List<ChatRoomInfomation> hostList;
    private Context context;
    public ChatRoomRecyclerViewAdapter(Context context, List<ChatRoomInfomation>  hostList) {

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
        holder.host.setText(hostList.get(position).getHostName());
        holder.desc.setText(hostList.get(position).getId().toString());
        holder.participant_num.setText(hostList.get(position).getParticipant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat formatterToString = new SimpleDateFormat("HH:mm");
        String time = "";
        try {
            Date date = formatter.parse(hostList.get(position).getTime().split(" ~ ")[0]);
            time += formatterToString.format(date) + "~";
            date = formatter.parse(hostList.get(position).getTime().split(" ~ ")[1]);
            time += formatterToString.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        2023-06-03T15:00 ~ 2023-06-03T18:00
        holder.time.setText(time);
        if (!hostList.get(position).getPrivate_())
            holder.private_.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return hostList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView host;
        TextView participant_num;
        TextView time;

        ImageView private_;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            host = itemView.findViewById(R.id.hostNickname_text);
            desc = itemView.findViewById(R.id.title_text);
            participant_num = itemView.findViewById(R.id.title_text2);
            time = itemView.findViewById(R.id.operating_time);
            private_ = itemView.findViewById(R.id.imageView2);
        }
    }
}
