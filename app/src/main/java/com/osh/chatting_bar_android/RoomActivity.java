package com.osh.chatting_bar_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private MessagesRecyclerViewAdapter MessagesRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        InitBtn();
        InitChatting();
    }

    protected void InitChatting(){
        RecyclerView recyclerView = findViewById(R.id.chatting_recyclerView);

        MessagesRecyclerViewAdapter = new MessagesRecyclerViewAdapter(this, getMessageList());
        recyclerView.setAdapter(MessagesRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    protected void InitBtn(){
        //닫기
        Button exit_button = findViewById(R.id.Exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainActivity.finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
        //방 제목 변경
        TextView RoomTitle = findViewById(R.id.room_name);
        RoomTitle.setText("test");


        //전송버튼
        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.chat_input);
                String str = editText.getText().toString();

                if(str.length() == 0){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else {

                }
            }
        });
    }
    private List<String> getMessageList() {
        return Arrays.asList("테스트 메세지 123", "테스트 메세지 456", "테스트 메세지 789", "장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지", "장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지장문 테스트 메세지");

    }

}
