package com.osh.chatting_bar_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.osh.chatting_bar_android.data_model.Categories;
import com.osh.chatting_bar_android.data_model.UserResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTagRecyclerViewAdapter extends RecyclerView.Adapter<EditTagRecyclerViewAdapter.MyViewHolder>{
    private List<Categories> tagList;
    private Context context;

    public EnumSet<Categories> getUserTagList() {
        return userTagList;
    }

    private EnumSet<Categories> userTagList; //체크한 태그들 리스트
    public EditTagRecyclerViewAdapter(Context context, List<Categories> tagList) {
        this.tagList = tagList;
        this.context = context;
        if (User.getInstance().getCategories() != null)
            userTagList = User.getInstance().getCategories();
        else
            userTagList = EnumSet.noneOf(Categories.class);
    }
    @NonNull
    @Override
    public EditTagRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EditTagRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText("#"+ tagList.get(position).getStr());
        if (userTagList.contains(Categories.valueOfStr(tagList.get(position).getStr())))
        {
            holder.textView.setBackgroundResource(R.drawable.round_rect_select_tag);
        }
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tag_name_text);
            LinearLayout tagBtn = itemView.findViewById(R.id.tag_select_btn);


            tagBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable currentBackground = textView.getBackground();
                    int noSelectTagResource = R.drawable.round_rect_no_select_tag;
                    int selectTagResource = R.drawable.round_rect_select_tag;

                    Drawable noSelectTagDrawable = ContextCompat.getDrawable(context, noSelectTagResource);

                    if (currentBackground != null && currentBackground.getConstantState().equals(noSelectTagDrawable.getConstantState())) {
                        textView.setBackgroundResource(selectTagResource);
                        userTagList.add(Categories.valueOfStr(textView.getText().toString().replaceAll("#", "")));
                    }
                    else{
                        textView.setBackgroundResource(noSelectTagResource);
                        userTagList.remove(Categories.valueOfStr(textView.getText().toString().replaceAll("#", "")));
                    }
                }
            });

// Helper method to get resource name from drawable

//            아이템 클릭 이벤트 처리
//            LinearLayout recentBtn = itemView.findViewById(R.id.recent_button);
//            recentBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MainActivity.mainActivity.finish();
//                    Intent intent = new Intent(v.getContext(), MainActivity.class);
//                    intent.putExtra("search", textView.getText().toString());
//
//                    ((SearchActivity)v.getContext()).updateSearchList(textView.getText().toString());
//
//                    v.getContext().startActivity(intent);
//
//                   ((Activity)v.getContext()).finish();
//                }
//            });
        }
    }
}
