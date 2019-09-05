package com.preprepare.prepare.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.preprepare.prepare.Model.MyModel;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("Select * from questionTable")
    List<MyModel> getAllQuestion();

    @Insert
    void inserAll(QuestionSet... questionSets);

    @Delete
    void deleteAll();
}
