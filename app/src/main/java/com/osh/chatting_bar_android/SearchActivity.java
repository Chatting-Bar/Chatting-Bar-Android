package com.osh.chatting_bar_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText editText = findViewById(R.id.editSearch);
        ImageButton search_btn = findViewById(R.id.searching_button);
        Button exit_btn = findViewById(R.id.Exit_button);
        RecyclerView recyclerView = findViewById(R.id.recent_search_view);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, getList());

        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //검색
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

        //닫기
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }
    private List<String> getList(){
        return Arrays.asList("축구", "리그 오브 레전드", "롤토체스", "test");
    }
}
