package com.example.mobicow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "mcow.db";

        // User table name
        private static final String TABLE_USER = "user";
      private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_COW = "cow";
        // User Table Columns names
        private static final String COLUMN_USER_ID = "user_id";
        private static final String COLUMN_USER_NAME = "user_name";
        private static final String COLUMN_USER_EMAIL = "user_email";
        private static final String COLUMN_USER_PASSWORD = "user_password";
        //cow table columns
        private static final String COLUMN_COW_AGE = "cow_age";
    private static final String COLUMN_COW_BREED= "cow_breed";
    private static final String COLUMN_COW_MILK= "cow_milk";
    private static final String COLUMN_COW_COST= "cow_cost";


    // create table sql query
        private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    private String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_COW_TABLE = "CREATE TABLE " + TABLE_COW+ "("
            + COLUMN_COW_AGE + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_COW_BREED + " TEXT,"
            + COLUMN_COW_MILK + " TEXT," + COLUMN_COW_COST + " TEXT" + ")";

        // drop table sql query
        private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
       private String DROP_ADMIN_TABLE = "DROP TABLE IF EXISTS " + TABLE_ADMIN;
       private String DROP_COW_TABLE = "DROP TABLE IF EXISTS " + TABLE_COW;

        /**
         * Constructor
         *
         * @param context
         */
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_ADMIN_TABLE);
            db.execSQL(CREATE_COW_TABLE);

        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //Drop User Table if exist
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_ADMIN_TABLE);
            db.execSQL(DROP_COW_TABLE);

            // Create tables again
            onCreate(db);

        }

        public void addUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cows = new ContentValues();
            cows.put(COLUMN_COW_AGE, user.getAge());
            cows.put(COLUMN_COW_BREED, user.getBreed());
            cows.put(COLUMN_COW_MILK, user.getMilk());
            cows.put(COLUMN_COW_COST, user.getCost());
            db.insert(TABLE_COW, null, cows);

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, user.getName());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());
            db.insert(TABLE_USER, null, values);


            db.close();
        }
        //update password
    //forget password field

    public void updatePassword(String email,String pass){
            SQLiteDatabase db =this.getWritableDatabase();
           ContentValues values = new ContentValues();
           values.put(COLUMN_USER_PASSWORD, pass);
           db.update(TABLE_USER,values,COLUMN_USER_EMAIL+"= ?",new String[]{email});
           db.close();

    }
    public void updateAdminPassword(String email,String pass){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, pass);
        db.update(TABLE_ADMIN,values,COLUMN_USER_EMAIL+"= ?",new String[]{email});
        db.close();

    }

        /**
         * This method is to fetch all user and return the list of user records
         *
         * @return list
         */
        public List<User> getAllUser() {
            // array of columns to fetch
            String[] columns = {
                    COLUMN_COW_AGE,
                    COLUMN_COW_BREED,
                    COLUMN_COW_MILK,
                    COLUMN_COW_COST

            };
            // sorting orders
            String sortOrder =
                    COLUMN_COW_BREED + " ASC";
            List<User> userList = new ArrayList<User>();

            SQLiteDatabase db = this.getReadableDatabase();

            // query the user table
            /**
             * Here query function is used to fetch records from user table this function works like we use sql query.
             * SQL query equivalent to this query function is
             * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
             */
            Cursor cursor = db.query(TABLE_COW, //Table to query
                    columns,    //columns to return
                    null,        //columns for the WHERE clause
                    null,        //The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    sortOrder); //The sort order


            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COW_AGE))));
                    user.setBreed(cursor.getString(cursor.getColumnIndex(COLUMN_COW_BREED)));
                    user.setMilk(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COW_MILK))));
                    user.setCost(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            // return user list
            return userList;
        }

        /**
         * This method to update user record
         *
         * @param user
         */
        public void updateUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, user.getName());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());

            // updating row
            db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
            db.close();
        }

        /**
         * This method is to delete user record
         *
         * @param user
         */
        public void deleteUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete user record by id
            db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
            db.close();
        }

        /**
         * This method to check user exist or not
         *
         * @param email
         * @return true/false
         */
        public boolean checkUser(String email) {

            // array of columns to fetch
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getReadableDatabase();

            // selection criteria
            String selection = COLUMN_USER_EMAIL + " = ?";

            // selection argument
            String[] selectionArgs = {email};

            // query user table with condition
            /**
             * Here query function is used to fetch records from user table this function works like we use sql query.
             * SQL query equivalent to this query function is
             * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
             */
            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                      //filter by row groups
                    null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }

            return false;
        }
    public boolean checkCow(String breed) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_COW_AGE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_COW_BREED + " = ?";

        // selection argument
        String[] selectionArgs = {breed};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_COW, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkAdmin(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_ADMIN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

        /**
         * This method to check user exist or not
         *
         * @param email
         * @param password
         * @return true/false
         */
        public boolean checkUser(String email, String password) {

            // array of columns to fetch
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getReadableDatabase();
            // selection criteria
            String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

            // selection arguments
            String[] selectionArgs = {email, password};

            // query user table with conditions
            /**
             * Here query function is used to fetch records from user table this function works like we use sql query.
             * SQL query equivalent to this query function is
             * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
             */
            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                      //The sort order

            int cursorCount = cursor.getCount();

            cursor.close();
            db.close();
            if (cursorCount > 0) {
                return true;
            }

            return false;
        }
    public boolean checkAdmin(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_ADMIN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    }

