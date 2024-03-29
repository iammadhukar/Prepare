package com.preprepare.prepare.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.preprepare.prepare.Firebase.MyFirebaseDatabase;
import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Room.MyAppDatabase;
import com.preprepare.prepare.Room.QuestionSet;
import com.preprepare.prepare.ViewModel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyRepository extends ViewModel {

    private static String TAG = "MyRepository";

    private MyFirebaseDatabase myFirebaseDatabase;
    private LiveData<List<MyModel>> liveData;
    public MyAppDatabase myAppDatabase;
    private Context context;
    private List<MyModel> questionList;
    public DeleteAsyncTask deleteAsyncTask;
    private int position;
    private GetQuestionAsyncTask getQuestionAsyncTask;
    private MyModel questionSet;
    private MyViewModel myViewModel;
    private MutableLiveData<MyModel> questionliveData;
    private String selectedAnswer;

    public MyRepository(Context context, MyViewModel myViewModel){
        myFirebaseDatabase = new MyFirebaseDatabase(this);
        liveData = new MutableLiveData<>();
        this.context = context;
        this.myViewModel = myViewModel;
        questionList = new ArrayList<>();
        deleteAsyncTask = new DeleteAsyncTask();
//        getQuestionAsyncTask = new GetQuestionAsyncTask();
        questionliveData = new MutableLiveData<>();
    }

    public LiveData<List<MyModel>> getDataFromFirebase() {
        liveData =  myFirebaseDatabase.getQuestionListLiveData();

        Log.d(TAG, "Size while fetching is : "+questionList.size());
        return liveData;
    }

    public void saveDataToRoom(List<MyModel> listLiveData){
        questionList = listLiveData;
        myAppDatabase = MyAppDatabase.getInstance(context);
        Log.d(TAG, "Size is : "+questionList.size());
        new InsertAsyncTask().execute();

    }

    public void deleteData(){
        deleteAsyncTask.execute();
    }

    public LiveData<MyModel> getQuestionDetails(int position){
        this.position=position;
        getQuestionAsyncTask = new GetQuestionAsyncTask();

        if (myAppDatabase == null){
            return questionliveData;
        }else{
            getQuestionAsyncTask.execute();

            return questionliveData;
        }
    }

    public void updateSelectedAnswer(String selectedAnswer){
        this.selectedAnswer = selectedAnswer;
        UpdateSelectedAsyncTask updateSelectedAsyncTask = new UpdateSelectedAsyncTask();
        updateSelectedAsyncTask.execute();

    }

    public void deleteDatabase(){
        MyAppDatabase.destroyInstance();
    }

    class InsertAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (MyModel data : questionList){
                myAppDatabase.questionDao().inserAll(new QuestionSet(data));
            }
//            myAppDatabase.questionDao().inserAll(new QuestionSet(questionList));
            Log.d(TAG, "Thread name is : "+Thread.currentThread());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Data saved in Room");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getQuestionDetails(1);
                }
            });

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

    class GetQuestionAsyncTask extends AsyncTask<Void,Void,MyModel>{

        @Override
        protected MyModel doInBackground(Void... voids) {
            Log.d(TAG, "Data is fetching from Room");
            Log.d(TAG, "position is : "+position);
            Log.d(TAG, "Question : "+ myAppDatabase.questionDao().getQuestionDetails(position).getQuestion());
            return myAppDatabase.questionDao().getQuestionDetails(position);
        }

        @Override
        protected void onPostExecute(MyModel aVoid) {
            super.onPostExecute(aVoid);
            questionSet = aVoid;
            questionliveData.setValue(questionSet);
            Log.d(TAG,"Question is "+ questionSet.getQuestion());
        }
    }

    class UpdateSelectedAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            myAppDatabase.questionDao().updateSelectedAnswer(selectedAnswer,position);
            return null;
        }
    }

    public void onFinish(){
        if (myFirebaseDatabase!=null)
            myFirebaseDatabase=null;
        if (myAppDatabase!=null)
            myAppDatabase=null;
    }


}
