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

public class ReclycerAdapterbx extends RecyclerView.Adapter<ReclyclerAdapter.ViewHolder>{
    private Context context;
    private List<String> list;
    private ArrayList<String> place;
    private ArrayList<String> placenum;
    private ArrayList<String> cause;
    public final static int maxSize =10000000;
    public ReclycerAdapterbx(Context context,
                              ArrayList<String>cause, ArrayList<String> place, ArrayList<String> placenum) {
        this.context = context;
        this.place = place;
        this.placenum = placenum;
        this.cause = cause;
    }

    @NonNull
    @NotNull
    @Override
    public ReclyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layoutbx, parent, false);
        return new ReclyclerAdapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReclyclerAdapter.ViewHolder holder, int position) {
        holder.tvNumber.setText(cause.get(position));
        holder.tvName.setText(place.get(position));
        holder.tvGender.setText(placenum.get(position));

        holder.cvShow.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(context,addbaoxiu.class);
                                                 intent.putExtra("type", addbaoxiu.BX_EDIT);
                                                 intent.putExtra("sno", MainActivity.SNO);
                                                 context.startActivity(intent);
                                             }
                                         }
        );

    }
    @Override
    public int getItemCount() {
        if (cause.size() < maxSize)
            return cause.size();
        else
            return maxSize;
    }
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TableLayout cvShowqj;
        public TextView tcause;
        public TextView time1;
        public TextView time2;

        public ViewHolder2(@NonNull @NotNull View itemView) {
            super(itemView);
            cvShowqj = (TableLayout) itemView.findViewById(R.id.cv_showbx);
            tcause = (TextView) itemView.findViewById(R.id.tv_show_number);
            time1 = (TextView) itemView.findViewById(R.id.tv_show_name);
            time2 = (TextView) itemView.findViewById(R.id.tv_show_gender);
        }
    }

}
