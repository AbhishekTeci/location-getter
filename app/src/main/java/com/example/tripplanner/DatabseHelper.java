package com.example.tripplanner;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = dataHolder.class, exportSchema = false, version = 1)
public abstract class DatabseHelper extends RoomDatabase {

    private static final String DB_NAME = "databaseHelperDB";
    private static DatabseHelper instance;


    public static synchronized DatabseHelper getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract Rider_DAO rider_dao();
}
