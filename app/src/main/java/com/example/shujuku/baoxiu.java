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

public class baoxiu extends AppCompatActivity {
ImageButton button;
    private DatabaseHelper mStudentDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    RecyclerView rvMain;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoxiu);
        mStudentDateBaseHelper = new DatabaseHelper(this, "user_db", null, 8);
        mSQLiteDatabase = mStudentDateBaseHelper.getReadableDatabase();
        mSharedPreferences = this.getSharedPreferences("bx", MODE_PRIVATE);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        button=findViewById(R.id.imageButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baoxiu.this, addbaoxiu.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(baoxiu.this,studentMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initView();
    }
    private void initView() {
        rvMain = (RecyclerView) findViewById(R.id.recyclerView2);
        rvMain.setLayoutManager(new LinearLayoutManager(baoxiu.this));
    }
    protected void onStart() {
        super.onStart();
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        ArrayList<String> cause = new ArrayList<>();
        ArrayList<String> place = new ArrayList<>();
        ArrayList<String> placenum = new ArrayList<>();
        mSQLiteDatabase=mStudentDateBaseHelper.getWritableDatabase();
        Cursor mCursor = mSQLiteDatabase.query("bx", null, null, null, null, null, null);
        int size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
        while (true) {
            if (size-- == 0)
                break;
            mCursor.moveToNext();
            cause.add(mCursor.getString(mCursor.getColumnIndex("cause")));
            place.add(mCursor.getString(mCursor.getColumnIndex("place")));
            placenum.add(mCursor.getString(mCursor.getColumnIndex("placenum")));
        }
        mCursor.close();
        rvMain.setAdapter(new ReclycerAdapterbx(baoxiu.this,cause, place, placenum));
    }
}