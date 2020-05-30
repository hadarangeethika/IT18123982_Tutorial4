package com.example.tutorial4.Database;

import android.provider.BaseColumns;

public final class UsersMaster {
    private UsersMaster(){}

    /*inner class that defines the table contents*/
    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
