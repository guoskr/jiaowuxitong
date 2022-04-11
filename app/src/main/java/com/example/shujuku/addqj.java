package com.example.shujuku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class addqj extends AppCompatActivity implements View.OnClickListener {
    TextView t1,t2;
    TextView e1,e2,e3,e4,e5;
    TextView delete;
    ImageView cause,time1,time2;
    ImageButton button1,button2;
    public static final int QJ_ADD = 111;
    public static final int QJ_EDIT = 222;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private String initNumber;
    private int currentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addqj);
        initNumber = MainActivity.SNO;
        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
        db = dbHelper.getWritableDatabase();
        receiveType();
        initView();
        receiveInfo();
    }
    private void initView() {
        t1=findViewById(R.id.textView666);
        t1.setText(initNumber);
        t2=findViewById(R.id.textView20);
        Cursor cursor1 = db.query("student", null, "sno=?", new String[]{initNumber}, null, null, null);
        if (cursor1.moveToNext()) {
            do {
                String names = cursor1.getString(cursor1.getColumnIndex("sname"));
                t2.setText(names);
            } while (cursor1.moveToNext());

        }
        e1=findViewById(R.id.tv_edit_gender);
        e2=findViewById(R.id.tv_edit_birth);
        e3=findViewById(R.id.tv_edit_time);
        e4=findViewById(R.id.et_edit_native_place);
        e5=findViewById(R.id.et_edit_phone);
        delete=findViewById(R.id.de);
        delete.setOnClickListener(this);
        cause=findViewById(R.id.trgender);
        cause.setOnClickListener(this);
        time1=findViewById(R.id.trbirth);
        time1.setOnClickListener(this);
        time2=findViewById(R.id.tiem);
        time2.setOnClickListener(this);
        button1=findViewById(R.id.imageButton);
        button1.setOnClickListener(this);
        button2=findViewById(R.id.imageButton5);
        button2.setOnClickListener(this);
        switch (currentType) {
            case QJ_ADD:
                //如果是增添信息模式，就让删除按钮不可见
                delete.setVisibility(View.GONE);
                break;
            case QJ_EDIT:
                delete.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void receiveInfo() {
        if (currentType == QJ_EDIT) {
            String sno;
            String sname;
            String cause;
            String timestart;
            String timefinal;
            String name;
            String phone;
            Intent intent = this.getIntent();
            Cursor cursor = db.query("leave", null, "sno=?", new String[]{initNumber}, null, null, null);
            if (cursor.moveToNext()) {
                do {
                    sno = cursor.getString(cursor.getColumnIndex("sno"));
                    sname = cursor.getString(cursor.getColumnIndex("sname"));
                    cause = cursor.getString(cursor.getColumnIndex("cause"));
                    timestart = cursor.getString(cursor.getColumnIndex("timestart"));
                    timefinal = cursor.getString(cursor.getColumnIndex("timefinal"));
                    name=cursor.getString(cursor.getColumnIndex("name"));
                    phone = cursor.getString(cursor.getColumnIndex("phone"));
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
    }
    private void receiveType() {
        Intent intent = this.getIntent();
        currentType = intent.getIntExtra("type", QJ_ADD);
    }
    private void tvDeleteAction() {
        new AlertDialog.Builder(addqj.this)
                .setTitle("删除请假信息？")
                .setMessage("确认删除此请假信息？\n学号：" + initNumber)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("leave", "sno=?", new String[]{initNumber});
                        finish();
                        Toast.makeText(addqj.this, "该信息删除成功！", Toast.LENGTH_SHORT).show();
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    private boolean notSameNumber(String sno) {
        Intent intent = this.getIntent();
        Cursor cursor = db.query("leave", null, "sno=?", new String[]{sno}, null, null, null);
        if (cursor.getCount() == 0 ||(currentType==QJ_EDIT && sno.equals(initNumber))) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
    private void trGenderAction() {
        final String[] arrayGender = new String[]{"事假", "病假","其他"};
        new AlertDialog.Builder(addqj.this)
                .setTitle("请假原因")
                .setItems(arrayGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        e1.setText(arrayGender[which]);
                    }
                })
                .create()
                .show();
    }
    private void trBirthAction1() {
        final DatePicker dpBirth = (DatePicker) getLayoutInflater().inflate(R.layout.dialog_edit_birth, null);

        new AlertDialog.Builder(addqj.this)
                .setTitle("修改请假日期")
                .setView(dpBirth)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将Activity中的textview显示AlertDialog中EditText中的内容
                        //并且用Toast显示一下
                        e2.setText(dpBirth.getYear() + "年" + (dpBirth.getMonth() + 1) + "月" + dpBirth.getDayOfMonth() + "日");
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    private void trBirthAction2() {
        final DatePicker dpBirth = (DatePicker) getLayoutInflater().inflate(R.layout.dialog_edit_birth, null);

        new AlertDialog.Builder(addqj.this)
                .setTitle("修改请假日期")
                .setView(dpBirth)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将Activity中的textview显示AlertDialog中EditText中的内容
                        //并且用Toast显示一下
                        e3.setText(dpBirth.getYear() + "年" + (dpBirth.getMonth() + 1) + "月" + dpBirth.getDayOfMonth() + "日");
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    private boolean notNull(String sno, String sname, String cause, String time1, String time2, String name, String phone) {
        if (cause.equals(""))
            return false;
        if (time1.equals(""))
            return false;
        if (time2.equals(""))
            return false;
        if (name.equals(""))
            return false;
        if (phone.equals(""))
            return false;
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trgender:
                trGenderAction();
                break;
            case R.id.trbirth:
                trBirthAction1();
                break;
            case R.id.tiem:
                trBirthAction2();
                break;
            case R.id.imageButton:
                Intent intent = new Intent(addqj.this, qingjia.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageButton5:
                //Cursor cursor = db.query("student", null, null, null, null, null, null);
                String sno = t1.getText().toString();
                String sname = t2.getText().toString();
                String cause = e1.getText().toString();
                String time1 = e2.getText().toString();
                String time2 = e3.getText().toString();
                String name = e4.getText().toString();
                String phone = e5.getText().toString();
                if (notNull(sno, sname, cause, time1, time2,name,phone)) {
                    if (notSameNumber(sno)) {
                        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
                        db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("sno", t1.getText().toString().trim());
                        values.put("sname", t2.getText().toString().trim());
                        values.put("cause", e1.getText().toString().trim());
                        values.put("timestart", e2.getText().toString().trim());
                        values.put("timefinal", e3.getText().toString().trim());
                        values.put("name", e4.getText().toString().trim());
                        values.put("phone", e5.getText().toString().trim());
                        switch (currentType) {
                            case QJ_ADD:
                                db.insert("leave", null, values);
                                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(addqj.this, qingjia.class);
                                startActivity(intent1);
                                finish();
                                break;
                            case QJ_EDIT:
                                db.update("leave", values, "sno=?", new String[]{initNumber});
                                Toast.makeText(this, "数据修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(addqj.this, qingjia.class);
                                startActivity(intent2);
                                finish();
                                break;
                        }
                    } else {
                        Toast.makeText(this, "已经存在请假信息", Toast.LENGTH_SHORT).show();
                        break;
                    }
                } else {
                    Toast.makeText(this, "数据不可为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.de:
                tvDeleteAction();
                break;
        }

    }

}