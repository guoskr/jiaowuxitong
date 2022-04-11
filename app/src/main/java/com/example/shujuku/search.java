package com.example.shujuku;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class search extends Activity {

    private ImageButton btnFront;
    private EditText etSearch;
    private RecyclerView rvSearch;
    //数据存储
    private DatabaseHelper mStudentDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    //变量与常量
    public static final int TYPE_SEARCH_NUMBER = 11;
    public static final int TYPE_SEARCH_NAME = 22;
    public static final int TYPE_SEARCH_sdept = 33;
    private int currentSearchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        receiveSearchType();
        initView();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //将状态栏颜色设置为与toolbar一致
            getWindow().setStatusBarColor(getResources().getColor(R.color.normal_blue));
        }
        btnFront = findViewById(R.id.imageButton4);
        btnFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etSearch = (EditText) findViewById(R.id.et_search);
        switch (currentSearchType) {
            case TYPE_SEARCH_NUMBER:
                etSearch.setHint("请输入你要搜索的学生学号");
                break;
            case TYPE_SEARCH_NAME:
                etSearch.setHint("请输入你要搜索的学生姓名");
                break;
            case TYPE_SEARCH_sdept:
                etSearch.setHint("请输入你要搜索的学生专业");
                break;
        }
        rvSearch = (RecyclerView) findViewById(R.id.rv_search);
        rvSearch.setLayoutManager(new LinearLayoutManager(search.this));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshRecyclerView(s + "");
            }

        });
    }
    private void refreshRecyclerView(String s) {
        ArrayList<String> sno = new ArrayList<>();
        ArrayList<String> sname = new ArrayList<>();
        ArrayList<String> sdept = new ArrayList<>();
        mStudentDateBaseHelper = new DatabaseHelper(this, "user_db", null, 8);
        mSQLiteDatabase = mStudentDateBaseHelper.getReadableDatabase();
        Cursor mCursor;
        switch (currentSearchType) {
            case TYPE_SEARCH_NAME:
                mCursor = mSQLiteDatabase.query("student", null, "sname like ?", new String[]{"%" + s + "%"}, null, null, null);
                break;
            case TYPE_SEARCH_sdept:
                mCursor = mSQLiteDatabase.query("student", null, "sdept like ?", new String[]{"%" + s + "%"}, null, null, null);
                break;
            default:
                mCursor = mSQLiteDatabase.query("student", null, "sno like ?", new String[]{"%" + s + "%"}, null, null, null);
        }
       int  size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
        while (true) {
            if (size-- == 0)
                break;
            mCursor.moveToNext();
            sno.add(mCursor.getString(mCursor.getColumnIndex("sno")));
            sname.add(mCursor.getString(mCursor.getColumnIndex("sname")));
            sdept.add(mCursor.getString(mCursor.getColumnIndex("sdept")));
        }
        mCursor.close();
        rvSearch.setAdapter(new searchadapter(search.this, sno,sname,sdept));
    }


    private void receiveSearchType() {
        Intent intent = this.getIntent();
        currentSearchType = intent.getIntExtra("search_type", TYPE_SEARCH_NUMBER);
    }
}
