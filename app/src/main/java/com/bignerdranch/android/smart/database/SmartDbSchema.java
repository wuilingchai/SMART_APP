package com.bignerdranch.android.smart.database;

public class SmartDbSchema {
    public static final class SmartTable{
        public static final String NAME = "goals";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String SPECIFIC = "specific";
            public static final String MEASURABLE = "measurable";
            public static final String ATTAINABLE = "attainable";
            public static final String RELEVANT = "relevant";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String COMPLETED = "completed";
            public static final String GALLERY = "gallery";

        }
    }
}
