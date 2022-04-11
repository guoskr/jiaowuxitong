package com.example.shujuku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class xsxx extends AppCompatActivity {
    private DatabaseHelper mStudentDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    SharedPreferences mSharedPreferences;
    RecyclerView rvMain;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("学生信息");
        FloatingActionButton fab = findViewById(R.id.fl3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(xsxx.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mStudentDateBaseHelper = new DatabaseHelper(this, "user_db", null, 8);
        mSQLiteDatabase = mStudentDateBaseHelper.getReadableDatabase();
        mSharedPreferences = this.getSharedPreferences("student", MODE_PRIVATE);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(xsxx.this,MainActivity_mianjiemian.class);
                startActivity(intent);
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchAction();
                return false;
            }
        });
    }
    private void initView() {
        rvMain = (RecyclerView) findViewById(R.id.ry1);
        rvMain.setLayoutManager(new LinearLayoutManager(xsxx.this));
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
        Cursor mCursor = mSQLiteDatabase.query("student", null, null, null, null, null, null);
        int size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
        while (true) {
            if (size-- == 0)
                break;
            mCursor.moveToNext();
            number.add(mCursor.getString(mCursor.getColumnIndex("sno")));
            name.add(mCursor.getString(mCursor.getColumnIndex("sname")));
            gender.add(mCursor.getString(mCursor.getColumnIndex("ssex")));
        }
        mCursor.close();
        rvMain.setAdapter(new ReclyclerAdapterxk(xsxx.this, number, name, gender));
    }
    private void searchAction() {
        final String[] arrayGender = new String[]{"按学号查找", "按姓名查找", "按专业查找"};
        new AlertDialog.Builder(xsxx.this)
                .setTitle("搜索类型")
                .setItems(arrayGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(xsxx.this, search.class);
                        switch (which) {
                            case 0:
                                intent.putExtra("search_type", search.TYPE_SEARCH_NUMBER);
                                break;
                            case 1:
                                intent.putExtra("search_type", search.TYPE_SEARCH_NAME);
                                break;
                            case 2:
                                intent.putExtra("search_type", search.TYPE_SEARCH_sdept);
                                break;
                        }
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity_mianjiemian, menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        return true;
    }

}
