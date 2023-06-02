package com.osh.chatting_bar_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.osh.chatting_bar_android.data_model.ChatRoomInfomation;
import com.osh.chatting_bar_android.data_model.ChatRoomResponse;
import com.osh.chatting_bar_android.data_model.UserResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ChatRoomRecyclerViewAdapter ChatRoomRecyclerViewAdapter;

    public static Activity mainActivity;

    private List<ChatRoomInfomation> latesetinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = MainActivity.this;

        Call<ChatRoomResponse> call = RetrofitService.getApiTokenService().getLatestRoom();
        call.enqueue(new Callback<ChatRoomResponse>() {
            //콜백 받는 부분
            @Override
            public void onResponse(Call<ChatRoomResponse> call, Response<ChatRoomResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("test", "최신순\n"+response.body().toString() + ", code: " + response.code());
                    latesetinfo = response.body().getInformation();
                    InitRoomList(latesetinfo, findViewById(R.id.newest_recyclerView));
//                    InitRoomList(latesetinfo, findViewById(R.id.searchResult_recyclerView));
//                    InitRoomList(latesetinfo, findViewById(R.id.subscribe_recyclerView));
                } else {
                    try {
                        Log.d("test", "최신방"+response.errorBody().string() + ", code: " + response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ChatRoomResponse> call, Throwable t) {
                Log.d("test", "실패: " + t.getMessage());

                Toast.makeText(getApplicationContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        });

        call = RetrofitService.getApiTokenService().getRecommendRoom();
        call.enqueue(new Callback<ChatRoomResponse>() {
            //콜백 받는 부분
            @Override
            public void onResponse(Call<ChatRoomResponse> call, Response<ChatRoomResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("test", "추천순\n"+response.body().toString() + ", code: " + response.code());
                    latesetinfo = response.body().getInformation();
                    InitRoomList(latesetinfo, findViewById(R.id.recommend_recyclerView));
                } else {
                    try {
                        Log.d("test", "최신방"+response.errorBody().string() + ", code: " + response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ChatRoomResponse> call, Throwable t) {
                Log.d("test", "실패: " + t.getMessage());

                Toast.makeText(getApplicationContext(), "네트워크 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        });


        InitBtn();

        blindSearchResult();

        Intent intent = getIntent();
        if (intent.hasExtra("search")) {
            String str = intent.getStringExtra("search");
            TextView textView = findViewById(R.id.searchWord_text);
            textView.setText("\""+str+"\"");
            showSearchResult();
        }
    }

    //검색결과 가리기
    protected void blindSearchResult(){
        LinearLayout searchResultlayout = findViewById(R.id.search_result);
        searchResultlayout.setVisibility(View.GONE);//GONE = 공간까지 가림
                                                    //참고 : https://kdsoft-zeros.tistory.com/102
    }
    protected void showSearchResult(){
        LinearLayout searchResultlayout = findViewById(R.id.search_result);
        searchResultlayout.setVisibility(View.VISIBLE);
    }

    protected void InitRoomList(List<ChatRoomInfomation> chatRoomList, RecyclerView recyclerView){
        ChatRoomRecyclerViewAdapter = new ChatRoomRecyclerViewAdapter(this, chatRoomList);
        recyclerView.setAdapter(ChatRoomRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


    }
    protected void InitBtn()
    {


        Button searchcancel_btn = findViewById(R.id.searchCancel_button);
        searchcancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blindSearchResult();
            }
        });
        
        //채팅 기록
        Button chatlist_btn = findViewById(R.id.chattingList_button);
        chatlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatLogActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //홈
        Button home_btn = findViewById(R.id.on_home_button);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
        //검색
        Button search_btn = findViewById(R.id.search_button);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //설정
        Button setting_btn = findViewById(R.id.setting_button);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

                finish();
            }
        });
        //방만들기
        FloatingActionButton create_btn = findViewById(R.id.fab_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
                startActivity(intent);
            }
        });

        // 채팅방 테스트 케이스
        ImageButton alarm_btn = findViewById(R.id.alarm_button);
        alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> getNewestList() {
        return Arrays.asList("배수호", "오시현", "백계환", "배종찬", "신초은");

    }
    private List<String> getRecommendList() {
        return Arrays.asList("추천 테스트", "오시현2", "백계환2", "배종찬2", "신초은2");

    }
    private List<String> getSearchResultList() {
        return Arrays.asList("검색어 테스트", "오시현3", "백계환3", "배종찬3", "신초은3");

    }
    private List<String> getSubscribeList() {
        return Arrays.asList("구독자", "오시현3", "백계환3", "배종찬3", "신초은3");

    }
}