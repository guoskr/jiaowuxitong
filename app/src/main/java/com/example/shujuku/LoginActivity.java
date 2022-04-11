 package com.example.shujuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String SNO=null;
    Button button;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int loginflag;
    EditText editText1;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=findViewById(R.id.button3);
        button.setOnClickListener(this);
        dbHelper=new DatabaseHelper(this,"user_db",null,8);
        db=dbHelper.getWritableDatabase();
        loginflag=0;
        editText1=findViewById(R.id.editTextTextPersonName3);
        editText2=findViewById(R.id.editTextTextPassword2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:

                    Cursor cursor = db.query("user", null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            String username = cursor.getString(cursor.getColumnIndex("username"));
                            String password = cursor.getString(cursor.getColumnIndex("password"));
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
                                    Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity_mianjiemian.class);
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
                        Toast toast = Toast.makeText(LoginActivity.this, "账户不存在或密码错误", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    }
                    editText1.setText("");
                    editText2.setText("");
                    break;
                    //这里写可能出现异常的语句



        }
    }
}