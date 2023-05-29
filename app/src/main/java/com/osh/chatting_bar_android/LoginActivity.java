package com.osh.chatting_bar_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    public static Activity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivity = LoginActivity.this;

        InitBtn();
    }

    protected void InitBtn()
    {
        //회원가입 버튼
        Button sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(intent);
            }
        });
        //로그인 버튼
        Button login = findViewById(R.id.login_enter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id_input = findViewById(R.id.ID_input);
                EditText pw_input = findViewById(R.id.PW_input);
                //아이디 비번이 틀린 경우
                if (id_input.getText().toString().isEmpty() || !isValidEmail(id_input.getText().toString())
                || pw_input.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호를 잘못 입력했습니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        });
        //구글 로그인 버튼
        Button google = findViewById(R.id.google_enter);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //구글 로그인 액션
            }
        });
        //비번찾기 버튼
        Button find = findViewById(R.id.PW_find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PW_find.class);
                startActivity(intent);
            }
        });
        //종료 버튼
        Button back = findViewById(R.id.Exit_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
//                finish();
            }
        });
    }

    //이메일 체크 정규식
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
}