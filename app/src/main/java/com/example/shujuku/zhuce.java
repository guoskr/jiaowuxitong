package com.example.shujuku;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mob.MobSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class zhuce extends AppCompatActivity implements View.OnClickListener {
    String APPKEY = "33456049388d8";
    String APPSECRETE = "dfd5a4ec165a2f4c61f582c271ff3f55";
    Button button;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    Button button1;
    int i = 30;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        dbHelper=new DatabaseHelper(this,"user_db",null,8);
        db=dbHelper.getWritableDatabase();
        init();
    }

    private void init() {
        button = findViewById(R.id.button2);
        button.setOnClickListener(this);
        editText1 = findViewById(R.id.editTextTextPassword2);
        editText2 = findViewById(R.id.editTextTextPassword3);
        editText3 = findViewById(R.id.editTextTextPersonName);
        editText4 = findViewById(R.id.phone);
        editText5 = findViewById(R.id.phone2);
        button1 = findViewById(R.id.button4);
        button1.setOnClickListener(this);
        MobSDK.init(this, APPKEY, APPSECRETE);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //????????????????????????
        SMSSDK.registerEventHandler(eventHandler);

    }

    @Override
    public void onClick(View v) {
        String phoneNums = editText4.getText().toString();
        switch (v.getId()) {
            case R.id.button2:
                flag=false;
                if (editText1.getText().toString().equals("") || editText3.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(zhuce.this, "???????????????????????????", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (editText2.getText().toString().equals(editText1.getText().toString())) {
                        Cursor cursor = db.query("student", null, null, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            do {
                                String sno = cursor.getString(cursor.getColumnIndex("sno"));
                                if(editText3.getText().toString().equals(sno)){
                                    SMSSDK.submitVerificationCode("86", phoneNums, editText5.getText().toString());
                                    dbHelper=new DatabaseHelper(this,"user_db",null,8);
                                    db=dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("susername",editText3.getText().toString());
                                    try {
                                        MessageDigest digest =MessageDigest.getInstance("md5");
                                        String password =editText1.getText().toString();
                                        byte [] result = digest.digest(password.getBytes());
                                        String str="";
                                        for (byte b:result)
                                        {
                                            int number = b&0xff;
                                            str+=Integer.toHexString(number);
                                        }
                                        values.put("spassword",str);
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                    }
                                    db.insert("studentuser",null,values);
                                    values.clear();
                                    flag=true;
                                    break;
                                }
                            }while (cursor.moveToNext());
                        }
                        if(flag==false)
                        {
                            Toast toast = Toast.makeText(zhuce.this, "??????????????????", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                else
                    {
                        Toast toast = Toast.makeText(zhuce.this, "?????????????????????", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }

                break;
            case R.id.button4:
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. ??????sdk??????????????????
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. ?????????????????????????????????????????????????????????????????????
                button1.setClickable(false);
                button1.setText("????????????(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();

                break;
        }

    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                button1.setText("????????????(" + i + ")");
            } else if (msg.what == -8) {
                button1.setText("???????????????");
                button1.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // ??????????????????????????????MainActivity,????????????
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// ?????????????????????
                        Toast.makeText(getApplicationContext(), "????????????",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(zhuce.this,
                                MainActivity.class);
                        startActivity(intent);
                    }else if(event==SMSSDK.RESULT_ERROR)
                    {
                        Toast.makeText(getApplicationContext(), "??????????????????????????????~",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Toast.makeText(getApplicationContext(), "?????????????????????",
                                    Toast.LENGTH_SHORT).show();
                        } }else {
                    String ret = data.toString().replace("java.lang.Throwable: ", "");
                    Log.v("guo",ret);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(ret);
                        int status = jsonObject.getInt("status");
                        Log.v("guo",status+"");
                        if (status == 468){
                            Toast.makeText(getApplicationContext(), "???????????????????????????~",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.v("guo",e.getMessage());
                        e.printStackTrace();
                    }
                            ((Throwable) data).printStackTrace();
                        }

            }
        }
    };

    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    public static boolean isMobileNO(String mobileNums) {
        /*
         * ?????????134???135???136???137???138???139???150???151???157(TD)???158???159???187???188
         * ?????????130???131???132???152???155???156???185???186 ?????????133???153???180???189??????1349?????????
         * ????????????????????????????????????1?????????????????????3???5???8???????????????????????????0-9
         */
        String telRegex = "[1][3578]\\d{9}";// "[1]"?????????1????????????1???"[358]"????????????????????????3???5???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}