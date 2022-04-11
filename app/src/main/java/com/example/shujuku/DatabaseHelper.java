package com.example.shujuku;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
 import android.database.sqlite.*;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.shujuku.ui.home.HomeFragment;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_user="create table user("
            +"id integer primary key autoincrement,"
            +"username text not null unique,"
            +"password text)";

    public static final String CREATE_student="create table student("
            +"id integer primary key autoincrement,"
            +"sno text not null unique,"
            +"sname text,"
            +"ssex text,"
            +"sage text,"
            +"dress text,"
            +"sdept text)";

    public static final String CREATE_studentuser="create table studentuser("
            +"id integer primary key autoincrement,"
            +"susername text not null unique,"
            +"spassword text)";

    public static final String CRETE_course="create table course("
            +"id integer primary key autoincrement,"
            +"cno text not null unique,"
            +"cname text,"
            +"credit text)";
    public static final String CRETE_leave="create table leave("
            +"id integer primary key autoincrement,"
            +"sno text not null unique,"
            +"sname text,"
            +"cause text,"
            +"timestart text ,"
            +"timefinal text,"
            +"name text,"
            +"phone text)";
    public static final String CRETE_sc="create table sc("
            +"id integer primary key autoincrement,"
            +"sno text not null unique,"
            +"sname text,"
            +"cno text,"
            +"cname text,"
            +"grade text)";
    public static final String CRETE_bx="create table bx("
            +"id integer primary key autoincrement,"
            +"sno text not null unique,"
            +"sname text,"
            +"place text,"
            +"placenum text,"
            +"cause text,"
            +"phone text)";

    private Context mContext;
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_user);
        db.execSQL(CREATE_student);
        db.execSQL(CREATE_studentuser);
        db.execSQL(CRETE_course);
        db.execSQL(CRETE_leave);
        db.execSQL(CRETE_sc);
        db.execSQL(CRETE_bx);
        Toast.makeText(mContext,"登录成功！",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists student");
        db.execSQL("drop table if exists studentuser");
        db.execSQL("drop table if exists course");
        db.execSQL("drop table if exists leave");
        db.execSQL("drop table if exists sc");
        db.execSQL("drop table if exists bx");
        onCreate(db);
    }
}
