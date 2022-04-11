package com.example.shujuku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class qingjia extends AppCompatActivity {
    ImageButton button;
    private DatabaseHelper mStudentDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    RecyclerView rvMain;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qingjia);
        mStudentDateBaseHelper = new DatabaseHelper(this, "user_db", null, 8);
        mSQLiteDatabase = mStudentDateBaseHelper.getReadableDatabase();
        mSharedPreferences = this.getSharedPreferences("leave", MODE_PRIVATE);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        button=findViewById(R.id.imageButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(qingjia.this, addqj.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(qingjia.this,studentMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initView();
    }

    private void initView() {
        rvMain = (RecyclerView) findViewById(R.id.recyclerView2);
        rvMain.setLayoutManager(new LinearLayoutManager(qingjia.this));
    }
    protected void onStart() {
        super.onStart();
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> gender = new ArrayList<>();
        mSQLiteDatabase=mStudentDateBaseHelper.getWritableDatabase();
        Cursor mCursor = mSQLiteDatabase.query("leave", null, null, null, null, null, null);
        int size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
        while (true) {
            if (size-- == 0)
                break;
            mCursor.moveToNext();
            number.add(mCursor.getString(mCursor.getColumnIndex("cause")));
            name.add(mCursor.getString(mCursor.getColumnIndex("timestart")));
            gender.add(mCursor.getString(mCursor.getColumnIndex("timefinal")));
        }
        mCursor.close();
        rvMain.setAdapter(new ReclyclerAdapterqj(qingjia.this, number, name, gender));
    }
}