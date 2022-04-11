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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class addbaoxiu extends AppCompatActivity implements View.OnClickListener {
    TextView t1,t2;
    TextView e1,e2,e3,e4;
    TextView delete;
    ImageView cause;
    ImageButton button1,button2;
    public static final int BX_ADD = 111;
    public static final int BX_EDIT = 222;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private String initNumber;
    private int currentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbaoxiu);
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
        e2=findViewById(R.id.et_edit_native_place);
        e3=findViewById(R.id.et_edit_cause);
        e4=findViewById(R.id.et_edit_phone);
        delete=findViewById(R.id.de);
        delete.setOnClickListener(this);
        cause=findViewById(R.id.trgender);
        cause.setOnClickListener(this);
        button1=findViewById(R.id.imageButton);
        button1.setOnClickListener(this);
        button2=findViewById(R.id.imageButton5);
        button2.setOnClickListener(this);
        switch (currentType) {
            case BX_ADD:
                //如果是增添信息模式，就让删除按钮不可见
                delete.setVisibility(View.GONE);
                break;
            case BX_EDIT:
                delete.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void receiveInfo() {
        if (currentType == BX_EDIT) {
            String sno;
            String sname;
            String place;
            String placenum;
            String cause;
            String phone;
            Intent intent = this.getIntent();
            Cursor cursor = db.query("bx", null, "sno=?", new String[]{initNumber}, null, null, null);
            if (cursor.moveToNext()) {
                do {
                    sno = cursor.getString(cursor.getColumnIndex("sno"));
                    sname = cursor.getString(cursor.getColumnIndex("sname"));
                    place = cursor.getString(cursor.getColumnIndex("place"));
                    placenum= cursor.getString(cursor.getColumnIndex("placenum"));
                    cause = cursor.getString(cursor.getColumnIndex("cause"));
                    phone = cursor.getString(cursor.getColumnIndex("phone"));
                    t1.setText(sno);
                    t2.setText(sname);
                    e1.setText(place);
                    e2.setText(placenum);
                    e3.setText(cause);
                    e4.setText(phone);
                } while (cursor.moveToNext());
            }
        }
    }
    private void receiveType() {
        Intent intent = this.getIntent();
        currentType = intent.getIntExtra("type", BX_ADD);
    }
    private void tvDeleteAction() {
        new AlertDialog.Builder(addbaoxiu.this)
                .setTitle("删除报修信息？")
                .setMessage("确认删除此请假信息？\n学号：" + initNumber)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("bx", "sno=?", new String[]{initNumber});
                        finish();
                        Toast.makeText(addbaoxiu.this, "该信息删除成功！", Toast.LENGTH_SHORT).show();
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    private boolean notSameNumber(String sno) {
        Intent intent = this.getIntent();
        Cursor cursor = db.query("bx", null, "sno=?", new String[]{sno}, null, null, null);
        if (cursor.getCount() == 0 ||(currentType==BX_EDIT && sno.equals(initNumber))) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
    private void trGenderAction() {
        final String[] arrayGender = new String[]{"馨宁", "馨源","厚朴"};
        new AlertDialog.Builder(addbaoxiu.this)
                .setTitle("宿舍楼")
                .setItems(arrayGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        e1.setText(arrayGender[which]);
                    }
                })
                .create()
                .show();
    }
    private boolean notNull(String sno, String sname, String place, String placenum, String cause, String phone) {
        if (cause.equals(""))
            return false;
        if (place.equals(""))
            return false;
        if (placenum.equals(""))
            return false;
        if (cause.equals(""))
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
            case R.id.imageButton:
                Intent intent = new Intent(addbaoxiu.this, baoxiu.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageButton5:
                //Cursor cursor = db.query("student", null, null, null, null, null, null);
                String sno = t1.getText().toString();
                String sname = t2.getText().toString();
                String place = e1.getText().toString();
                String placenum= e2.getText().toString();
                String cause = e3.getText().toString();
                String phone = e4.getText().toString();
                if (notNull(sno, sname, place, placenum, cause,phone)) {
                    if (notSameNumber(sno)) {
                        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
                        db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("sno", t1.getText().toString().trim());
                        values.put("sname", t2.getText().toString().trim());
                        values.put("place", e1.getText().toString().trim());
                        values.put("placenum", e2.getText().toString().trim());
                        values.put("cause", e3.getText().toString().trim());
                        values.put("phone", e4.getText().toString().trim());
                        switch (currentType) {
                            case BX_ADD:
                                db.insert("bx", null, values);
                                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(addbaoxiu.this, baoxiu.class);
                                startActivity(intent1);
                                finish();
                                break;
                            case BX_EDIT:
                                db.update("bx", values, "sno=?", new String[]{initNumber});
                                Toast.makeText(this, "数据修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(addbaoxiu.this, baoxiu.class);
                                startActivity(intent2);
                                finish();
                                break;
                        }
                    } else {
                        Toast.makeText(this, "已经存在报修信息", Toast.LENGTH_SHORT).show();
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