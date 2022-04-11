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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class addxkxx extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3;
    TextView delete;
    private ImageButton imagebutton;
    private ImageButton imageButton2;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    public static final int TYPE_ADD = 111;
    public static final int TYPE_EDIT = 222;
    private int currentType;
    private String initNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addxkxx);
        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
        db = dbHelper.getWritableDatabase();
        receiveType();
        initView();
        receiveInfo();
    }
    private void receiveInfo() {
        if (currentType == TYPE_EDIT) {
            String sno;
            String sname;
            String credit;
            Intent intent = this.getIntent();
            initNumber = intent.getStringExtra("cno");
            Cursor cursor = db.query("course", null, "sno=?", new String[]{initNumber}, null, null, null);
            if (cursor.moveToNext()) {
                do {
                    sno = cursor.getString(cursor.getColumnIndex("cno"));
                    sname = cursor.getString(cursor.getColumnIndex("cname"));
                    credit = cursor.getString(cursor.getColumnIndex("credit"));
                    e1.setText(sno);
                    e2.setText(sname);
                    e3.setText(credit);
                } while (cursor.moveToNext());
            }
        }
    }

    private void receiveType() {
        Intent intent = this.getIntent();
        currentType = intent.getIntExtra("type", TYPE_ADD);
    }

    private void initView() {

        imagebutton = findViewById(R.id.imageButton);
        imagebutton.setOnClickListener(this);
        imageButton2 = findViewById(R.id.imageButton3);
        imageButton2.setOnClickListener(this);
        e1 = findViewById(R.id.et_edit_number);
        e2 = findViewById(R.id.et_edit_name);
        e3 = findViewById(R.id.et_edit_native_place);
        delete = findViewById(R.id.de);
        delete.setOnClickListener(this);
        switch (currentType) {
            case TYPE_ADD:
                //如果是增添信息模式，就让删除按钮不可见
                delete.setVisibility(View.GONE);
                break;
            case TYPE_EDIT:
                delete.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                Intent intent = new Intent(addxkxx.this, xkxx.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageButton3:
                //Cursor cursor = db.query("student", null, null, null, null, null, null);
                String sno = e1.getText().toString();
                String sname = e2.getText().toString();
                String credit = e3.getText().toString();
                if (notNull(sno, sname, credit)) {
                    if (notSameNumber(sno)) {
                        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
                        db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("cno", e1.getText().toString().trim());
                        values.put("cname", e2.getText().toString().trim());
                        values.put("credit", e3.getText().toString().trim());
                        switch (currentType) {
                            case TYPE_ADD:
                                db.insert("course", null, values);
                                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(addxkxx.this, xkxx.class);
                                startActivity(intent1);
                                finish();
                                break;
                            case TYPE_EDIT:
                                db.update("course", values, "cno=?", new String[]{initNumber});
                                Toast.makeText(this, "数据修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(addxkxx.this, xkxx.class);
                                startActivity(intent2);
                                finish();
                                break;
                        }
                    } else {
                        Toast.makeText(this, "该学号已经存在", Toast.LENGTH_SHORT).show();
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

    private void tvDeleteAction() {
        new AlertDialog.Builder(addxkxx.this)
                .setTitle("删除课程信息？")
                .setMessage("确认删除此课程信息？\n学号：" + initNumber)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("course", "cno=?", new String[]{initNumber});
                        db.delete("sc", "cno=?", new String[]{initNumber});
                        finish();
                        Toast.makeText(addxkxx.this, "该信息删除成功！", Toast.LENGTH_SHORT).show();
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private boolean notSameNumber(String sno) {
        Intent intent = this.getIntent();
        initNumber = intent.getStringExtra("cno");
        Cursor cursor = db.query("course", null, "cno=?", new String[]{sno}, null, null, null);
        if (cursor.getCount() == 0 ||(currentType==TYPE_EDIT && sno.equals(initNumber))) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


    private boolean notNull(String sno, String sname, String credit) {
        if (sno.equals(""))
            return false;
        if (sname.equals(""))
            return false;
        if (credit.equals(""))
            return false;
        return true;
    }
}