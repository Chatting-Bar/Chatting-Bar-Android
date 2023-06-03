package com.osh.chatting_bar_android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class RoomDetailInfoPopupDialog extends Dialog {
    private Button shutdownClick;
    private LinearLayout completeClick;

    @SuppressLint("MissingInflatedId")
    public RoomDetailInfoPopupDialog(@NonNull Context context) {
        super(context);
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
                dismiss();
            }
        });
    }
}