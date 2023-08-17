package com.example.pregproject.other;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private  static  SQLiteOpenHelper mInstance;

    private MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //2.对外提供函数
    public static synchronized SQLiteOpenHelper getmInstance(Context context){//上下文得从调用处获取
        if(mInstance==null){
            mInstance=new MySQLiteOpenHelper(context,"derryDB.db",null,1);//版本号只能从1开始，每次升级就+1
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        /**
         * 建立user相关的表--------------------------------------------------------------------------------------------------------------------------------------------------
         */

        String sql = "create table user(_id integer primary key autoincrement,name text,password text,identity text)";
        sqLiteDatabase.execSQL(sql);

        /**
         * 建立好友相关的表------------------------------------------------------------------------------------------------------------------------------------------------
         */
        //好友申请
        sql = "create table friend_apply(_id integer primary key autoincrement,s_name text,r_name text,msg text,date text)";
        sqLiteDatabase.execSQL(sql);
        //好友列表
        sql="create table friend(_id integer primary key autoincrement,u_name text,f_name text,relationship text)";
        sqLiteDatabase.execSQL(sql);

        /**
         * 建立分享日常相关的表------------------------------------------------------------------------------------------------------------------------------------------------
         */
        sql = "create table publicc(_id integer primary key autoincrement,user text,title text,content text,count_flower text,pic1 BLOB,pic2 BLOB,pic3 BLOB,date text)";
        sqLiteDatabase.execSQL(sql);

        /**
         * 建立亲情账户接受的消息相关的表------------------------------------------------------------------------------------------------------------------------------------------------
         */
        sql = "create table new_notification(_id integer primary key autoincrement,accepter text,kind text,the_id text,date text)";
        sqLiteDatabase.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
