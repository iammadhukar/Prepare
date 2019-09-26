package com.preprepare.prepare.Room;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.preprepare.prepare.Model.FetchCorrectAndSelectedAnswer;
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

    @Query("UPDATE questionTable SET optionSelected =:selectedOption WHERE uid =:position ")
    void updateSelectedAnswer(String selectedOption, int position);

    @Query("SELECT optionSelected , answer from questionTable WHERE uid=:position")
    FetchCorrectAndSelectedAnswer calculateResult(int position );

}
