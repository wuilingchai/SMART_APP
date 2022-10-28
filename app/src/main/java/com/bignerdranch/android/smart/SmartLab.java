package com.bignerdranch.android.smart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.smart.database.SmartBaseHelper;
import com.bignerdranch.android.smart.database.SmartCursorWrapper;
import com.bignerdranch.android.smart.database.SmartDbSchema.SmartTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SmartLab {
    private static SmartLab sSmartLab;

    private Context mContext;
    private SQLiteDatabase  mDatabase;

    public static SmartLab get (Context context){
        if (sSmartLab == null){
            sSmartLab = new SmartLab(context);
        }
        return sSmartLab;
    }

    private SmartLab (Context context){
        mContext = context.getApplicationContext();
        mDatabase = new SmartBaseHelper(mContext)
                .getWritableDatabase();


    }

    public void addGoal (Smart g){
        ContentValues values = getContentValues(g);
        mDatabase.insert(SmartTable.NAME, null, values);
    }

    public List<Smart> getSmart(){
        List <Smart> smarts = new ArrayList<>();

        SmartCursorWrapper cursor = querySmart(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                smarts.add(cursor.getSmart());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return smarts;
    }

    public Smart getSmart(UUID id){
        SmartCursorWrapper cursor =querySmart(
                SmartTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSmart();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Smart smart){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, smart.getPhotoFilename());
    }

    public void updateGoals (Smart smart){
        String uuidString = smart.getId().toString();
        ContentValues values = getContentValues(smart);

        mDatabase.update(SmartTable.NAME, values,
                SmartTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private SmartCursorWrapper querySmart (String whereClause, String [] whereArgs){
        Cursor cursor = mDatabase.query(
                SmartTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SmartCursorWrapper(cursor);
    }

    private static ContentValues getContentValues (Smart smart){
        ContentValues values = new ContentValues();
        values.put(SmartTable.Cols.UUID, smart.getId().toString());
        values.put(SmartTable.Cols.TITLE, smart.getTitle());
        values.put(SmartTable.Cols.SPECIFIC, smart.getSpecific());
        values.put(SmartTable.Cols.MEASURABLE, smart.getMeasurable());
        values.put(SmartTable.Cols.ATTAINABLE, smart.getAttainable());
        values.put(SmartTable.Cols.RELEVANT, smart.getRelevant());
        values.put(SmartTable.Cols.DATE, smart.getDate().getTime());
        values.put(SmartTable.Cols.TIME, smart.getTime().getTime());
        values.put(SmartTable.Cols.COMPLETED, smart.isCompleted() ? 1 : 0);
        values.put(SmartTable.Cols.GALLERY, smart.getGallery());

        return values;
    }
}
