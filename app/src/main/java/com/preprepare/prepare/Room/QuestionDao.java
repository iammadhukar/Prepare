package com.preprepare.prepare.Room;

import android.util.Log;

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

    @Query("DELETE from questionTable")
    void deleteAll();

    @Query("Select * from questionTable WHERE uid=:position")
    MyModel getQuestionDetails(int position);
}
