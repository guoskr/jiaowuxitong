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

public class ReclyclerAdapterqj extends RecyclerView.Adapter<ReclyclerAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private ArrayList<String> mcause;
    private ArrayList<String> mtime1;
    private ArrayList<String> mtime2;
    public final static int maxSize =10000000;
    public ReclyclerAdapterqj(Context context,
                            ArrayList<String> cause, ArrayList<String> time1, ArrayList<String> time2) {
        this.context = context;
        this.mcause = cause;
        this.mtime1 = time1;
        this.mtime2 = time2;
    }
    @NonNull
    @NotNull
    @Override
    public ReclyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.qjlayout, parent, false);
        return new ReclyclerAdapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReclyclerAdapter.ViewHolder holder, int position) {
        holder.tvNumber.setText(mcause.get(position));
        holder.tvName.setText(mtime1.get(position));
        holder.tvGender.setText(mtime2.get(position));

        holder.cvShow.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(context,addqj.class);
                                                 intent.putExtra("type", addqj.QJ_EDIT);
                                                 intent.putExtra("sno", MainActivity.SNO);
                                                 context.startActivity(intent);
                                             }
                                         }
        );
    }

    @Override
    public int getItemCount() {
        if (mcause.size() < maxSize)
            return mcause.size();
        else
            return maxSize;
    }
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public TableLayout cvShowqj;
        public TextView tcause;
        public TextView time1;
        public TextView time2;

        public ViewHolder1(@NonNull @NotNull View itemView) {
            super(itemView);
            cvShowqj = (TableLayout) itemView.findViewById(R.id.cv_showqj);
            tcause = (TextView) itemView.findViewById(R.id.tv_show_number);
            time1 = (TextView) itemView.findViewById(R.id.tv_show_name);
            time2 = (TextView) itemView.findViewById(R.id.tv_show_gender);
        }
    }
}
