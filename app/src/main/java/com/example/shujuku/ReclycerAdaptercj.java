package com.example.shujuku;

import android.content.Context;
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

public class ReclycerAdaptercj extends RecyclerView.Adapter<ReclyclerAdapter.ViewHolder>{
    private Context context;
    private List<String> list;
    private ArrayList<String> course;
    private ArrayList<String> grade;
    public final static int maxSize =10000000;
    public ReclycerAdaptercj(Context context,
                             ArrayList<String>course, ArrayList<String> grade) {
        this.context = context;
        this.course = course;
        this.grade= grade;
    }
    @NonNull
    @NotNull
    @Override
    public ReclyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layoutbxcj, parent, false);
        return new ReclyclerAdapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReclyclerAdapter.ViewHolder holder, int position) {
        holder.tvNumber.setText(course.get(position));
        holder.tvName.setText(grade.get(position));

        holder.cvShow.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
//                                                 Intent intent = new Intent(context,addbaoxiu.class);
//                                                 intent.putExtra("type", addbaoxiu.BX_EDIT);
//                                                 intent.putExtra("sno", MainActivity.SNO);
//                                                 context.startActivity(intent);  /这需要修改/
                                             }
                                         }
        );

    }

    @Override
    public int getItemCount() {
        if (course.size() < maxSize)
            return course.size();
        else
            return maxSize;
    }
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TableLayout cvShowcj;
        public TextView course;
        public TextView grade;

        public ViewHolder2(@NonNull @NotNull View itemView) {
            super(itemView);
            cvShowcj = (TableLayout) itemView.findViewById(R.id.cv_showqj);
            course = (TextView) itemView.findViewById(R.id.tv_show_number);
            grade = (TextView) itemView.findViewById(R.id.tv_show_name);
        }
    }
}
