package com.example.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String COLUMN_FOOD_NAME = "FOOD_NAME";
    public static final String COLUMN_FOOD_CALORIES = "FOOD_CALORIES";
    public static final String COLUMN_FOOD_PROTEIN = "FOOD_PROTEIN";
    public static final String COLUMN_FOOD_FAT = "FOOD_FAT";
    public static final String COLUMN_FOOD_CARBS = "FOOD_CARBS";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "food.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FOOD_TABLE + " (ID INTAGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FOOD_NAME + " TEXT, " + COLUMN_FOOD_CALORIES + " DOUBLE, " + COLUMN_FOOD_PROTEIN + " DOUBLE, " + COLUMN_FOOD_FAT + " DOUBLE, " + COLUMN_FOOD_CARBS + " DOUBLE)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //public boolean addOne()
}
