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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class  AddActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView1;
    private TextView textView2;
    private TableRow trGender;
    private TableRow trBirth;
    private ImageButton imagebutton;
    private ImageButton imageButton2;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    EditText editText1;
    EditText editText2;
    TextView editText3;
    TextView editText4;
    EditText editText5;
    EditText editText6;
    TextView delete;
    public static final int TYPE_ADD = 111;
    public static final int TYPE_EDIT = 222;
    private int currentType;
    boolean iftag = true;
    private String initNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
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
            String ssex;
            String sage;
            String dress;
            String sdept;
            Intent intent = this.getIntent();
            initNumber = intent.getStringExtra("sno");
            Cursor cursor = db.query("student", null, "sno=?", new String[]{initNumber}, null, null, null);
            if (cursor.moveToNext()) {
                do {
                    sno = cursor.getString(cursor.getColumnIndex("sno"));
                    sname = cursor.getString(cursor.getColumnIndex("sname"));
                    ssex = cursor.getString(cursor.getColumnIndex("ssex"));
                    sage = cursor.getString(cursor.getColumnIndex("sage"));
                    dress = cursor.getString(cursor.getColumnIndex("dress"));
                    sdept = cursor.getString(cursor.getColumnIndex("sdept"));
                    editText1.setText(sno);
                    editText2.setText(sname);
                    editText3.setText(ssex);
                    editText4.setText(sage);
                    editText5.setText(dress);
                    editText6.setText(sdept);
                } while (cursor.moveToNext());
            }
        }
    }

    private void receiveType() {
        Intent intent = this.getIntent();
        currentType = intent.getIntExtra("type", TYPE_ADD);
    }

    private void initView() {
        textView1 = findViewById(R.id.tv_edit_gender);
        textView2 = findViewById(R.id.tv_edit_birth);
        trGender = (TableRow) findViewById(R.id.tr_edit_gender);
        trBirth = (TableRow) findViewById(R.id.tr_edit_birth);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        trGender.setOnClickListener(this);
        trBirth.setOnClickListener(this);
        imagebutton = findViewById(R.id.imageButton);
        imagebutton.setOnClickListener(this);
        imageButton2 = findViewById(R.id.imageButton3);
        imageButton2.setOnClickListener(this);
        editText1 = findViewById(R.id.et_edit_number);
        editText2 = findViewById(R.id.et_edit_name);
        editText3 = findViewById(R.id.tv_edit_gender);
        editText4 = findViewById(R.id.tv_edit_birth);
        editText5 = findViewById(R.id.et_edit_native_place);
        editText6 = findViewById(R.id.et_edit_phone);
        delete = findViewById(R.id.de);
        delete.setOnClickListener(this);
        switch (currentType) {
            case TYPE_ADD:
                //?????????????????????????????????????????????????????????
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
            case R.id.tr_edit_gender:
                trGenderAction();
                break;
            case R.id.tr_edit_birth:
                trBirthAction();
                break;
            case R.id.imageButton:
                Intent intent = new Intent(AddActivity.this, xsxx.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageButton3:
                //Cursor cursor = db.query("student", null, null, null, null, null, null);
                String sno = editText1.getText().toString();
                String sname = editText2.getText().toString();
                String ssex = editText3.getText().toString();
                String sage = editText4.getText().toString();
                String dress = editText5.getText().toString();
                String sdept = editText6.getText().toString();
                if (notNull(sno, sname, ssex, sage, dress, sdept)) {
                    if (notSameNumber(sno)) {
                        dbHelper = new DatabaseHelper(this, "user_db", null, 8);
                        db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("sno", editText1.getText().toString().trim());
                        values.put("sname", editText2.getText().toString().trim());
                        values.put("ssex", editText3.getText().toString().trim());
                        values.put("sage", editText4.getText().toString().trim());
                        values.put("dress", editText5.getText().toString().trim());
                        values.put("sdept", editText6.getText().toString().trim());
                        switch (currentType) {
                            case TYPE_ADD:
                                db.insert("student", null, values);
                                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(AddActivity.this, xsxx.class);
                                startActivity(intent1);
                                finish();
                                break;
                            case TYPE_EDIT:
                                db.update("student", values, "sno=?", new String[]{initNumber});
                                Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(AddActivity.this, xsxx.class);
                                startActivity(intent2);
                                finish();
                                break;
                        }
                    } else {
                        Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    }
                } else {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.de:
                tvDeleteAction();
                break;
        }
    }

    private void tvDeleteAction() {
        new AlertDialog.Builder(AddActivity.this)
                .setTitle("?????????????????????")
                .setMessage("??????????????????????????????\n?????????" + initNumber)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("student", "sno=?", new String[]{initNumber});
                        db.delete("studentuser", "sno=?", new String[]{initNumber});
                        db.delete("leave", "sno=?", new String[]{initNumber});
                        db.delete("sc", "sno=?", new String[]{initNumber});
                        finish();
                        Toast.makeText(AddActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                })
                //?????????????????????button?????????????????????????????????????????????null????????????
                .setNegativeButton("??????", null)
                .create()
                .show();
    }

    private boolean notSameNumber(String sno) {
        Intent intent = this.getIntent();
        initNumber = intent.getStringExtra("sno");
        Cursor cursor = db.query("student", null, "sno=?", new String[]{sno}, null, null, null);
        if (cursor.getCount() == 0 ||(currentType==TYPE_EDIT && sno.equals(initNumber))) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    private void trGenderAction() {
        final String[] arrayGender = new String[]{"???", "???"};
        new AlertDialog.Builder(AddActivity.this)
                .setTitle("????????????")
                .setItems(arrayGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView1.setText(arrayGender[which]);
                    }
                })
                .create()
                .show();
    }

    private void trBirthAction() {
        final DatePicker dpBirth = (DatePicker) getLayoutInflater().inflate(R.layout.dialog_edit_birth, null);

        new AlertDialog.Builder(AddActivity.this)
                .setTitle("??????????????????")
                .setView(dpBirth)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //???Activity??????textview??????AlertDialog???EditText????????????
                        //?????????Toast????????????
                        textView2.setText(dpBirth.getYear() + "???" + (dpBirth.getMonth() + 1) + "???" + dpBirth.getDayOfMonth() + "???");
                    }
                })
                //?????????????????????button?????????????????????????????????????????????null????????????
                .setNegativeButton("??????", null)
                .create()
                .show();
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