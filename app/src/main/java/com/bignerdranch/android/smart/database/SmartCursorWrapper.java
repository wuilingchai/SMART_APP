package com.bignerdranch.android.smart.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.smart.Smart;
import com.bignerdranch.android.smart.database.SmartDbSchema.SmartTable;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class SmartCursorWrapper extends CursorWrapper {
    public SmartCursorWrapper (Cursor cursor){
        super (cursor);
    }

    public Smart getSmart(){
        String uuidString = getString(getColumnIndex(SmartTable.Cols.UUID));
        String title = getString(getColumnIndex(SmartTable.Cols.TITLE));
        String specific = getString(getColumnIndex(SmartTable.Cols.SPECIFIC));
        String measurable = getString(getColumnIndex(SmartTable.Cols.MEASURABLE));
        String attainable = getString(getColumnIndex(SmartTable.Cols.ATTAINABLE));
        String relevant = getString(getColumnIndex(SmartTable.Cols.RELEVANT));
        long date = getLong(getColumnIndex(SmartTable.Cols.DATE));
        long time = getLong(getColumnIndex(SmartTable.Cols.TIME));
        int isCompleted = getInt(getColumnIndex(SmartTable.Cols.COMPLETED));
        String gallery = getString(getColumnIndex(SmartTable.Cols.GALLERY));

        Smart smart = new Smart(UUID.fromString(uuidString));
        smart.setTitle(title);
        smart.setSpecific(specific);
        smart.setMeasurable(measurable);
        smart.setAttainable(attainable);
        smart.setRelevant(relevant);
        smart.setDate(new Date(date));
        smart.setTime(new Date(time));
        smart.setCompleted(isCompleted != 0);
        smart.setGallery(gallery);

        return smart;
    }
}
