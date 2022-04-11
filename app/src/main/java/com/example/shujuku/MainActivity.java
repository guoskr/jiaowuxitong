package com.example.shujuku;

import androidx.appcompat.app.AppCompatActivity  ;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1 ;
    EditText editText1;
    EditText editText2;
    TextView textView1;
    TextView textView2;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int loginflag;
    public static String SNO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button3);
        editText1 = findViewById(R.id.editTextTextPersonName3);
        editText2 = findViewById(R.id.editTextTextPassword2);
        textView1=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView4);
        button1.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        editText1.setText("");
        editText2.setText("");
        dbHelper=new DatabaseHelper(this,"user_db",null,8);
        db=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username","user1");
        try {
            MessageDigest digest =MessageDigest.getInstance("md5");
            String password ="123";
            byte [] result = digest.digest(password.getBytes());
            String str="";
            for (byte b:result)
            {
                int number = b&0xff;
                str+=Integer.toHexString(number);
            }
            values.put("password",str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        db.insert("user",null,values);
        values.clear();
        values.put("username","user2");
        try {
            MessageDigest digest =MessageDigest.getInstance("md5");
            String password ="456";
            byte [] result = digest.digest(password.getBytes());
            String str="";
            for (byte b:result)
            {
                int number = b&0xff;
                str+=Integer.toHexString(number);
            }
            values.put("password",str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        db.insert("user",null,values);
        values.clear();
        values.put("username","suguan");
        try {
            MessageDigest digest =MessageDigest.getInstance("md5");
            String password ="789";
            byte [] result = digest.digest(password.getBytes());
            String str="";
            for (byte b:result)
            {
                int number = b&0xff;
                str+=Integer.toHexString(number);
            }
            values.put("password",str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        db.insert("user",null,values);
        values.clear();
        loginflag=0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                Cursor cursor = db.query("studentuser", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String username = cursor.getString(cursor.getColumnIndex("susername"));
                        String password = cursor.getString(cursor.getColumnIndex("spassword"));
                        String str="";
                        try {
                            MessageDigest digest =MessageDigest.getInstance("md5");
                            byte [] result = digest.digest(editText2.getText().toString().getBytes());
                            for (byte b:result)
                            {
                                int number = b&0xff;
                                str+=Integer.toHexString(number);
                            }
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        if (editText1.getText().toString().equals(username) && str.equals(password)) {
                            loginflag = 1;
                            SNO=username;
                            Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,studentMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } while (cursor.moveToNext());
                }
                    if (editText1.getText().toString().isEmpty()||editText2.getText().toString().isEmpty())
                    {
                        Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else if (loginflag == 0) {
                        Toast toast = Toast.makeText(MainActivity.this, "账户不存在或密码错误", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    }
                    editText1.setText("");
                    editText2.setText("");
                break;
            case R.id.textView:
                Intent intent1 = new Intent(MainActivity.this, zhuce.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.textView4:
                Intent intent2=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}