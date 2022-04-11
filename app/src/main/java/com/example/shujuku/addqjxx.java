package com.example.shujuku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class addqjxx extends AppCompatActivity implements View.OnClickListener {
    TextView t1,t2;
    TextView e1,e2,e3,e4,e5;
    TextView delete;
    ImageView cause,time1,time2;
    ImageButton button1,button2;
    public static final int QJ_SH = 0;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private String initNumber;
    private int currentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addqjxx);
        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
        db = dbHelper.getWritableDatabase();
        currentType=QJ_SH;
        receiveType();
        initView();
        receiveInfo();
    }

    private void receiveInfo() {

            String sno;
            String sname;
            String cause;
            String timestart;
            String timefinal;
            String name;
            String phone;
            Intent intent = this.getIntent();
            initNumber = intent.getStringExtra("sno");
            Cursor cursor = db.query("leave", null, "sno=?", new String[]{initNumber}, null, null, null);
            if (cursor.moveToNext()) {
                do {
                    sno = cursor.getString(cursor.getColumnIndex("sno"));
                    sname = cursor.getString(cursor.getColumnIndex("sname"));
                    cause = cursor.getString(cursor.getColumnIndex("cause"));
                    timestart = cursor.getString(cursor.getColumnIndex("timestart"));
                    timefinal= cursor.getString(cursor.getColumnIndex("timefinal"));
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    phone= cursor.getString(cursor.getColumnIndex("phone"));
                    t1.setText(sno);
                    t2.setText(sname);
                    e1.setText(cause);
                    e2.setText(timestart);
                    e3.setText(timefinal);
                    e4.setText(name);
                    e5.setText(phone);
                } while (cursor.moveToNext());
            }
        }

    private void receiveType() {
        Intent intent = this.getIntent();
        currentType = intent.getIntExtra("type", QJ_SH);
    }

    private void initView() {
        t1 = findViewById(R.id.textView666);
        t2 = findViewById(R.id.textView20);
        e1=findViewById(R.id.textView23);
        e2=findViewById(R.id.textView24);
        e3=findViewById(R.id.textView25);
        e4=findViewById(R.id.textView26);
        e5=findViewById(R.id.textView27);
        button1=findViewById(R.id.imageButton);
        button1.setOnClickListener(this);
        button2=findViewById(R.id.imageButton5);
        button2.setOnClickListener(this);
        delete = findViewById(R.id.de);
        delete.setOnClickListener(this);
        if(qjxx.xiss==0)
            delete.setVisibility(View.GONE);
        if (qjxx.xiss==1)
            delete.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageButton:
                Intent intent = new Intent(addqjxx.this, qjxx.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageButton5:
                //Cursor cursor = db.query("student", null, null, null, null, null, null);
                Intent intent1= new Intent(addqjxx.this, qjxx.class);
                startActivity(intent1);
                finish();
                Toast.makeText(addqjxx.this,"审核通过",Toast.LENGTH_SHORT).show();
                qjxx.xiss=1;
                break;
            case R.id.de:
                tvDeleteAction();
                break;
        }
    }

    private void tvDeleteAction() {
        new AlertDialog.Builder(addqjxx.this)
                .setTitle("删除学生请假信息？")
                .setMessage("确认删除此信息？\n学号：" + initNumber)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("leave", "sno=?", new String[]{initNumber});
                        finish();
                        Toast.makeText(addqjxx.this, "销假成功！", Toast.LENGTH_SHORT).show();
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private boolean notSameNumber(String sno) {
        Intent intent = this.getIntent();
        initNumber = intent.getStringExtra("sno");
        Cursor cursor = db.query("student", null, "sno=?", new String[]{sno}, null, null, null);
        if (cursor.getCount() == 0 ||(currentType==QJ_SH && sno.equals(initNumber))) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


    private boolean notNull(String sno, String sname, String ssex, String sage, String dress, String sdept) {
        if (sno.equals(""))
            return false;
        if (sname.equals(""))
            return false;
        if (ssex.equals(""))
            return false;
        if (sage.equals(""))
            return false;
        if (dress.equals(""))
            return false;
        if (sdept.equals(""))
            return false;
        return true;
    }

}