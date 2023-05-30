package com.osh.chatting_bar_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Splash extends AppCompatActivity {
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = getSharedPreferences("user", Activity.MODE_PRIVATE);

        moveMain(1);
    }

    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("test", pref.getString("AccessToken", ""));
                if (pref.getString("AccessToken", "").isEmpty()) { //토큰이 없으면 로그인 화면으로 전환
                    //new Intent(현재 context, 이동할 activity)
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                    startActivity(intent);    //intent 에 명시된 액티비티로 이동

                    finish();    //현재 액티비티 종료
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(intent);    //intent 에 명시된 액티비티로 이동

                    finish();    //현재 액티비티 종료
                }
            }
        }, 1000 * sec); // sec초 정도 딜레이를 준 후 시작
    }
}