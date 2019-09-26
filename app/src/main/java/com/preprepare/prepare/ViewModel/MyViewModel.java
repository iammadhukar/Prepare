package com.preprepare.prepare.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.preprepare.prepare.Model.FetchCorrectAndSelectedAnswer;
import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Repository.MyRepository;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel {

    private static final String TAG = "MyViewModel";

    public MyRepository myRepository;
    private static Context context;
    private MyModel myModel;
    private LiveData<MyModel> liveData = new MutableLiveData<>();
    private static MyViewModel myViewModel;
    private ArrayList<FetchCorrectAndSelectedAnswer> dataList;

    public static MyViewModel getInstance(Context mContext){
        if (myViewModel==null){
            context = mContext.getApplicationContext();
            myViewModel = new MyViewModel();
        }
        return myViewModel;
    }

    private MyViewModel(){
        myRepository = new MyRepository(context, this);
        LiveData<List<MyModel>> liveData = myRepository.getDataFromFirebase();
        //myRepository.saveDataToRoom(liveData);
    }

    public LiveData<MyModel> getQuestionLiveData(int position){
        return myRepository.getQuestionDetails(position);
    }

    public void updateSelectedAnswer(String selectedAnswer){
        myRepository.updateSelectedAnswer(selectedAnswer);
    }

    public void onFinish(){
        myModel=null;
        myRepository.onFinish();
        myRepository=null;
    }

    public LiveData<ArrayList<FetchCorrectAndSelectedAnswer>> calculateResult(){
        return myRepository.calculateResult();
    }

}
