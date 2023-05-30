package com.osh.chatting_bar_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.osh.chatting_bar_android.data_model.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
        editor = pref.edit();
        InitBtn();
    }

    protected void InitBtn()
    {
        //로그아웃
        LinearLayout logout_btn = findViewById(R.id.logout_button);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "로그아웃: " + pref.getString("AccessToken", "") + "\n" + pref.getString("RefreshToken", ""));
                Call<BaseResponse> call = RetrofitService.getApiService().sign_out(pref.getString("AccessToken", ""), new stringRequest(pref.getString("RefreshToken", "")));
                call.enqueue(new Callback<BaseResponse>(){
                    //콜백 받는 부분
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("test", response.body().toString() +", code: "+ response.code());
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);

                            finish();
                        } else {
                            Log.d("test",  response.message() + ", code: "+ response.code());
                            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.d("test", "실패: "+ t.getMessage());

                        Toast.makeText(getApplicationContext(), "네트워크 문제로 로그아웃에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}