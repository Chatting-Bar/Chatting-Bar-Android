package com.osh.chatting_bar_android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.osh.chatting_bar_android.data_model.ChatRoomInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomDetailInfoPopupDialog extends Dialog {
    private Button shutdownClick;
    private LinearLayout completeClick;

    private ChatRoomInformation information;


    @SuppressLint("MissingInflatedId")
    public RoomDetailInfoPopupDialog(@NonNull Context context, ChatRoomInformation information) {
        super(context);
        this.information = information;
        setContentView(R.layout.popup_room_detail_inform);
        shutdownClick = findViewById(R.id.close_button);
        shutdownClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        completeClick = findViewById(R.id.complete_click);
        completeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RoomActivity.class);
                v.getContext().startActivity(intent);

                dismiss();
            }
        });
        TextView timeText = findViewById(R.id.operating_time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat formatterToString = new SimpleDateFormat("HH:mm");
        String time = "";
        try {
            Date date = formatter.parse(information.getTime().split(" ~ ")[0]);
            time += formatterToString.format(date) + "~";
            date = formatter.parse(information.getTime().split(" ~ ")[1]);
            time += formatterToString.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeText.setText(time);

        TextView titleText = findViewById(R.id.title_text);
        titleText.setText(information.getDesc());

        TextView participantText = findViewById(R.id.title_text2);
        participantText.setText(information.getParticipant());

        TextView hostText = findViewById(R.id.textView12);
        hostText.setText(information.getHostName());

        ArrayList<TextView> tagViews = new ArrayList<>();
        tagViews.add(findViewById(R.id.textView5));
        tagViews.add(findViewById(R.id.textView9));
        tagViews.add(findViewById(R.id.textView10));
        tagViews.add(findViewById(R.id.textView11));
        for (int i = 0; i < 4; i++) {
            if (i < information.getCategories().size())
                tagViews.get(i).setText("#"+information.getCategories().toArray()[i].toString());
            else
                tagViews.get(i).setVisibility(View.INVISIBLE);
        }
    }
}