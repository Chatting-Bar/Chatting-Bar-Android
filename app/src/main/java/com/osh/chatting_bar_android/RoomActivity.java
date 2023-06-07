package com.osh.chatting_bar_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.osh.chatting_bar_android.data_model.BaseResponse;
import com.osh.chatting_bar_android.data_model.ChatRoomInformation;
import com.osh.chatting_bar_android.data_model.OneCharRoomResponse;
import com.osh.chatting_bar_android.firebase.ChatCallback;
import com.osh.chatting_bar_android.firebase.DatabaseManager;
import com.osh.chatting_bar_android.firebase.data.ChatRoomData;
import com.osh.chatting_bar_android.firebase.data.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoomActivity extends AppCompatActivity {

    TextView title;
    EditText chatInput;
    private MessagesRecyclerViewAdapter MessagesRecyclerViewAdapter;
    private RoomMemberRecyclerViewAdapter RoomMemberRecyclerViewAdapter;
    ChatRoomInformation information;
    Boolean isHost;
    SharedPreferences pref;
    public DatabaseManager db;
    public DatabaseReference chatDB;
    private String userRole;
    private long roomId;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        userRole = "Host";
        user = User.getInstance();
        pref = User.getInstance().getPreferences();
        db = new DatabaseManager();
        isHost = Boolean.TRUE;
        title = findViewById(R.id.room_name);
        chatInput = findViewById(R.id.chat_input);
        Intent intent = getIntent();
        roomId = intent.getLongExtra("RoomID", 0);
        Log.d("roomId",Long.toString(roomId));

        if (roomId != 0) {
            // 방 조회 부분
            Call<OneCharRoomResponse> call = RetrofitService.getApiTokenService().getRoomInfo(intent.getLongExtra("RoomID", 0));
            call.enqueue(new Callback<OneCharRoomResponse>() {
                //콜백 받는 부분
                @Override
                public void onResponse(Call<OneCharRoomResponse> call, Response<OneCharRoomResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("test", response.body().toString() + ", code: " + response.code());
                        information = response.body().getInformation();
                        title.setText(information.getName());
                        sendNotify(User.getInstance().getNickname());
                        initRoom();
                    } else {
                        try {
                            Log.d("test", "방 조회"+response.errorBody().string() + ", code: " + response.code());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OneCharRoomResponse> call, Throwable t) {
                    Log.d("test", "실패: " + t.getMessage());

                    Toast.makeText(getApplicationContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
        observeChat();
        InitMemberList();
        //방에 있는 유저 조회
        // API /api/chatroom/{roomId}/users에서
        // userNickname과 자신의 닉네임을 찾아 userRole을 비교
        if(userRole == "Host") {
            HostInit();
        }
        else {
            GuestInit();
        }
        InitBtn();
    }
    public void initRoom(){
        // 룸 ID로 룸조회하기
        db.inquiryRoom(roomId, new ChatCallback<ChatRoomData>() {

            @Override
            public void onCallback(ChatRoomData data) {
                // 호스트 확인 메소드
                Log.d("information",Long.toString(information.getHostId()));
                Log.d("datasnapshot data",Long.toString(data.masterId));
                if(data.masterId == information.getHostId()){
                    HostInit();
                }else{
                    GuestInit();
                }
                InitChatting();
            }
        });

    }
    protected void HostInit(){
            //구독 버튼 -> 전체 얼리기 버튼으로 변경
            Button allChatBan_btn = findViewById(R.id.subscribeOrAllban_button);
            allChatBan_btn.setText("전체 얼리기");
            allChatBan_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //전체 얼리기 기능
                }
            });
    }
    protected void GuestInit(){
        //
        Button subscribe_btn = findViewById(R.id.subscribeOrAllban_button);
        subscribe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //구독 기능

            }
        });
    }
    public void observeChat(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("firebase", "onChildAdded:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        db.mDatabaseRef.child("chat").child("roomId").child(Long.toString(roomId)).addChildEventListener(childEventListener);
    }
    protected void sendMessage(){
        long uid = user.getId();
        String userName = user.getNickname();
        String message = chatInput.getText().toString();
        Log.d("sendmessage",Long.toString(roomId));
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        db.sendMessage(roomId,new Message(uid,userName,dateFormat.format(date),message));
        chatInput.setText("");
    }
    protected void sendNotify(String userName){
        long uid = 0;
        String username = "notify";
        String message = userName+"님이 입장하셨습니다";
        Log.d("sendmessage",Long.toString(roomId));
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        db.sendMessage(roomId,new Message(uid,userName,dateFormat.format(date),message));
        chatInput.setText("");
    }

//    채팅 리사이클러뷰 불러오기
    protected void InitChatting(){
        db.inquiryChatList(roomId, new ChatCallback<ArrayList<Message>>() {
            @Override
            public void onCallback(ArrayList<Message> data) {
                RecyclerView recyclerView = findViewById(R.id.chatting_recyclerView);
                MessagesRecyclerViewAdapter = new MessagesRecyclerViewAdapter(RoomActivity.this, data);
                recyclerView.setAdapter(MessagesRecyclerViewAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(RoomActivity.this));
            }
        });


    }

    //방 참여자 불러오기
    protected void InitMemberList(){
        RecyclerView recyclerView = findViewById(R.id.member_recyclerView);
        RoomMemberRecyclerViewAdapter = new RoomMemberRecyclerViewAdapter(this, getMemberList(),userRole);
        recyclerView.setAdapter(RoomMemberRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void InitBtn(){
        //닫기
        Button exit_button = findViewById(R.id.Exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<BaseResponse> call = RetrofitService.getApiTokenService().roomExit(information.getId());
                call.enqueue(new Callback<BaseResponse>() {
                    //콜백 받는 부분
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("test", response.body().toString() + ", code: " + response.code());
                            long uid = 0;
                            String username = "notify";
                            String message = user.getNickname()+"님이 나갔습니다.";
                            Log.d("sendmessage",Long.toString(roomId));
                            long now = System.currentTimeMillis();
                            Date date = new Date(now);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            db.sendMessage(roomId,new Message(uid,user.getNickname(),dateFormat.format(date),message));
                            chatInput.setText("");
                            MainActivity.mainActivity.finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                Log.d("test", "방 나가기"+response.errorBody().string() + ", code: " + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.d("test", "실패: " + t.getMessage());

                        Toast.makeText(getApplicationContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //방 제목 변경
        TextView RoomTitle = findViewById(R.id.room_name);
        RoomTitle.setText("test");

//      메뉴 버튼
        Button menu_btn = findViewById(R.id.menu_button);
        NavigationView navigationView = findViewById(R.id.roomMenu_drawerLayout);
        navigationView.setVisibility(View.INVISIBLE);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              InitMemberList();
              navigationView.setVisibility(View.VISIBLE);
            }
        });

//        네비게이션뷰 닫기 버튼
        Button menuExit_btn = findViewById(R.id.roomMenuExit_button);
        menuExit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navigationView.setVisibility(View.INVISIBLE);
            }
        });

//        네비게이션뷰 방나가기 버튼
        LinearLayout roomExit = findViewById(R.id.room_exit_button);
        roomExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(information.getHostId() == user.getId()) {
                    Call<BaseResponse> call = RetrofitService.getApiTokenService().roomClose(new LongRequest(information.getId()));
                    call.enqueue(new Callback<BaseResponse>() {
                        //콜백 받는 부분
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("test", response.body().toString() + ", code: " + response.code());
                                MainActivity.mainActivity.finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                try {
                                    Log.d("test", "방 닫기"+response.errorBody().string() + ", code: " + response.code());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Log.d("test", "실패: " + t.getMessage());

                            Toast.makeText(getApplicationContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Call<BaseResponse> call = RetrofitService.getApiTokenService().roomExit(information.getId());
                    call.enqueue(new Callback<BaseResponse>() {
                        //콜백 받는 부분
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("test", response.body().toString() + ", code: " + response.code());
                                MainActivity.mainActivity.finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                try {
                                    Log.d("test", "방 나가기"+response.errorBody().string() + ", code: " + response.code());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Log.d("test", "실패: " + t.getMessage());

                            Toast.makeText(getApplicationContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


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
                    sendMessage();
                }
            }
        });
    }

    private List<String> getMemberList(){
        return Arrays.asList("배수호","배종찬","오시현","백계환","신초은");
    }

}
