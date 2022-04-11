package com.example.shujuku;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class searchadapter extends RecyclerView.Adapter<searchadapter.ViewHolder> {
    @NonNull
    @NotNull
    private Context mContext;

    private ArrayList<String> sno;
    private ArrayList<String> sname;
    private ArrayList<String> sdept;

    public final static int maxSize = 1000000000;
    public searchadapter(Context context, ArrayList<String> ssno, ArrayList<String>ssname, ArrayList<String> ssdept) {
        mContext = context;
        sno = ssno;
        sname = ssname;
        sdept=ssdept;
    }

    @Override

    public searchadapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search, parent, false);

        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull searchadapter.ViewHolder holder, int position) {
        holder.sno.setText(sno.get(position));
        holder.sname.setText(sname.get(position));
        holder.llSearch.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {//跳转到搜索到的学生界面
                                                   Intent intent = new Intent(mContext, AddActivity.class);
                                                   intent.putExtra("type", AddActivity.TYPE_EDIT);
                                                   intent.putExtra("sno",sno.get(position));
                                                   mContext.startActivity(intent);
                                               }
                                           }
        );

    }

    @Override
    public int getItemCount() {
        if (sno.size() < maxSize)
            return sno.size();
        else
            return maxSize;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llSearch;
        public TextView sno;
        public TextView sname;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            llSearch = (LinearLayout) itemView.findViewById(R.id.ll_search);
            sno = (TextView) itemView.findViewById(R.id.tv_search_number);
            sname = (TextView) itemView.findViewById(R.id.tv_search_name);
        }
    }
}
