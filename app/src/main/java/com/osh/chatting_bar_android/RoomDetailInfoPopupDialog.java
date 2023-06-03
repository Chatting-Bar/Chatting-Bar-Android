package com.osh.chatting_bar_android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.osh.chatting_bar_android.data_model.BaseResponse;
import com.osh.chatting_bar_android.data_model.ChatRoomInformation;
import com.osh.chatting_bar_android.data_model.UserResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                //방입장
                Call<BaseResponse> call = RetrofitService.getApiTokenService().roomEnter(new LongRequest(information.getId()));
                call.enqueue(new Callback<BaseResponse>() {
                    //콜백 받는 부분
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("test", response.body().toString() + ", code: " + response.code());
                        } else {
                            try {
                                Log.d("test", "방입장"+response.errorBody().string() + ", code: " + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(v.getContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.d("test", "실패: " + t.getMessage());

                        Toast.makeText(v.getContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                    }
                });

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