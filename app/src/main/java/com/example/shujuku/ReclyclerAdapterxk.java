package com.example.shujuku;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReclyclerAdapterxk extends RecyclerView.Adapter<ReclyclerAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private ArrayList<String> mcno;
    private ArrayList<String> mcname;
    private ArrayList<String> mcredit;
    public final static int maxSize =10000000;
    public ReclyclerAdapterxk(Context context,
                            ArrayList<String> number, ArrayList<String> name, ArrayList<String> gender) {
        this.context = context;
        this.mcno = number;
        this.mcname = name;
        this.mcredit = gender;
    }
    @NonNull
    @NotNull
    @Override
    public ReclyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layoutxk, parent, false);
        return new ReclyclerAdapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReclyclerAdapter.ViewHolder holder, int position) {
        holder.tvNumber.setText(mcno.get(position));
        holder.tvName.setText(mcname.get(position));
        holder.tvGender.setText(mcredit.get(position));

        holder.cvShow.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(context, AddActivity.class);
                                                 intent.putExtra("type", AddActivity.TYPE_EDIT);
                                                 intent.putExtra("cno", mcno.get(position));
                                                 context.startActivity(intent);
                                             }
                                         }
        );
    }

    @Override
    public int getItemCount() {
        if (mcno.size() < maxSize)
            return mcno.size();
        else
            return maxSize;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TableLayout cvShow;
        public TextView tvNumber;
        public TextView tvName;
        public TextView tvGender;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cvShow = (TableLayout) itemView.findViewById(R.id.cv_showqj);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_show_number);
            tvName = (TextView) itemView.findViewById(R.id.tv_show_name);
            tvGender = (TextView) itemView.findViewById(R.id.tv_show_gender);
        }
    }
}
