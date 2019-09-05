package com.preprepare.prepare.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.preprepare.prepare.Firebase.MyFirebaseDatabase;
import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Room.MyAppDatabase;
import com.preprepare.prepare.Room.QuestionSet;

import java.util.ArrayList;
import java.util.List;

public class MyRepository extends ViewModel {

    private static String TAG = "MyRepository";

    private MyFirebaseDatabase myFirebaseDatabase;
    private LiveData<List<MyModel>> liveData;
    private MyAppDatabase myAppDatabase;
    private Context context;
    private List<MyModel> questionList;
    public DeleteAsyncTask deleteAsyncTask;

    public MyRepository(Context context){
        myFirebaseDatabase = new MyFirebaseDatabase(this);
        liveData = new MutableLiveData<>();
        this.context = context;
        questionList = new ArrayList<>();
        deleteAsyncTask = new DeleteAsyncTask();

    }

    public LiveData<List<MyModel>> getDataFromFirebase() {
        liveData =  myFirebaseDatabase.getQuestionListLiveData();

//        myFirebaseDatabase.accessDatabase();
//        questionList = myFirebaseDatabase.getQuestionList();

        Log.d(TAG, "Size while fetching is : "+questionList.size());
        return liveData;
    }

    public void saveDataToRoom(List<MyModel> listLiveData){
        questionList = listLiveData;
        myAppDatabase = MyAppDatabase.getInstance(context);
        Log.d(TAG, "Size is : "+questionList.size());
        new InsertAsyncTask().execute();
//        myAppDatabase.questionDao().inserAll(new QuestionSet(questionList));
//        Log.d(TAG, "Data saved in Room");
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                myAppDatabase.questionDao().inserAll(new QuestionSet(questionList));
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                Log.d(TAG, "Data saved in Room");
//            }
//        };

    }

    public void deleteData(){
        deleteAsyncTask.execute();
    }

    class InsertAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            myAppDatabase.questionDao().inserAll(new QuestionSet(questionList));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Data saved in Room");
        }
    }

    class DeleteAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            myAppDatabase.questionDao().deleteAll();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Data deleted in Room");
        }
    }


}
