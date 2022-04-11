package com.example.shujuku;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class s extends RecyclerView.Adapter<searchadapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> cno;
    private ArrayList<String> cname;
    public final static int maxSize = 1000000000;
    public s(Context context, ArrayList<String> ssno, ArrayList<String>ssname) {
        mContext = context;
        cno = ssno;
        cname = ssname;
    }
    @NonNull
    @NotNull
    @Override
    public searchadapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.searchcourse, parent, false);

        return new searchadapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull searchadapter.ViewHolder holder, int position) {
        holder.sno.setText(cno.get(position));
        holder.sname.setText(cname.get(position));
        holder.llSearch.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {//跳转到搜索到的学生界面
                                                   Intent intent = new Intent(mContext, AddActivity.class);
                                                   intent.putExtra("type", AddActivity.TYPE_EDIT);
                                                   intent.putExtra("sno",cno.get(position));
                                                   mContext.startActivity(intent);
                                               }
                                           }
        );
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
