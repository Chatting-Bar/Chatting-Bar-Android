package com.osh.chatting_bar_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.osh.chatting_bar_android.data_model.BaseResponse;
import com.osh.chatting_bar_android.data_model.Categories;
import com.osh.chatting_bar_android.data_model.ChatRoomRequest;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    SharedPreferences pref;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        pref = User.getInstance().getPreferences();
        InitBtn();
    }

    protected void InitBtn(){
        //종료 버튼
        Button back = findViewById(R.id.Exit_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainActivity.finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

        Button create = findViewById(R.id.Create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "방생성: "+pref.getString("AccessToken", ""));
                Set<Categories> tempSet = new HashSet<>();
                tempSet.add(Categories.DANCE);
                tempSet.add(Categories.GAME);
                tempSet.add(Categories.SPORT);
                ChatRoomRequest chatRoomRequest = new ChatRoomRequest("채팅방1", "채팅방1 설명", tempSet,
                        new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 5, false, "");
                Call<BaseResponse> call = RetrofitService.getApiTokenService().createRoom(chatRoomRequest);
                call.enqueue(new Callback<BaseResponse>(){
                    //콜백 받는 부분
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("test", response.body().toString() +", code: "+ response.code());
                        } else {
                            try {
                                Log.d("test", "방생성 실패" + response.errorBody().string() + ", code: "+ response.code());
                            } catch (IOException e) {
                                Log.d("test", e.toString() + "!");
                            }
                            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.d("test", "실패: "+ t.getMessage());

                        Toast.makeText(getApplicationContext(), "네트워크 문제로 방생성에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
