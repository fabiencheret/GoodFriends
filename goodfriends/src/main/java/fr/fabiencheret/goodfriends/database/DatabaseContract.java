package fr.fabiencheret.goodfriends.database;

import android.provider.BaseColumns;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 19/05/13
 * Time: 20:39
 */
public class DatabaseContract {

    public class DebtColumns implements BaseColumns {

        public static final String TABLE_NAME = "debt";

        public static final String COLUMN_NAME_POSITIVE = "positive";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USERID = "userId";
        public static final String COLUMN_NAME_WHAT = "what";

        public DebtColumns() {}
    }

    public class UserColumns implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ID = "id";

        private UserColumns(){}

    }

}
