package com.osh.chatting_bar_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ChatRoomRecyclerViewAdapter ChatRoomRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitBtn();
        InitRoomList(getNewestList(),findViewById(R.id.newest_recyclerView));
        InitRoomList(getRecommendList(),findViewById(R.id.recommend_recyclerView));
        InitRoomList(getSearchResultList(),findViewById(R.id.searchResult_recyclerView));

    }

    //검색결과 가리기
    protected void blindSearchResult(){
        LinearLayout searchResultlayout = findViewById(R.id.search_result);
        searchResultlayout.setVisibility(View.GONE);//GONE = 공간까지 가림
                                                    //참고 : https://kdsoft-zeros.tistory.com/102
    }

    protected void InitRoomList(List<String> chatRoomList, RecyclerView recyclerView){
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

                finish();
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
}