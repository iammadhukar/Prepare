package com.preprepare.prepare.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QuestionSet.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase  {

    private static String DB_NAME = "AppDatabase";
    private static MyAppDatabase instance;
    public abstract QuestionDao questionDao();

    public static synchronized MyAppDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MyAppDatabase.class, DB_NAME)
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
