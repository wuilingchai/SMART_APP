package com.bignerdranch.android.smart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.smart.Smart;
import com.bignerdranch.android.smart.database.SmartDbSchema.SmartTable;

import java.util.UUID;

public class SmartBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "smartBase.db";

    public SmartBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + SmartTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SmartTable.Cols.UUID + "," +
                SmartTable.Cols.TITLE + "," +
                SmartTable.Cols.SPECIFIC + "," +
                SmartTable.Cols.MEASURABLE + "," +
                SmartTable.Cols.ATTAINABLE + "," +
                SmartTable.Cols.RELEVANT + "," +
                SmartTable.Cols.DATE + "," +
                SmartTable.Cols.TIME + "," +
                SmartTable.Cols.COMPLETED + "," +
                SmartTable.Cols.GALLERY +
                ")"
        );
    }

    public void deleteGoal(UUID id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(SmartTable.NAME, "UUID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }



}
