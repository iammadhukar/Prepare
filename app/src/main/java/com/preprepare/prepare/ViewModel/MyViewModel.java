package com.preprepare.prepare.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Repository.MyRepository;

import java.util.List;

public class MyViewModel {

    private static final String TAG = "MyViewModel";

    public MyRepository myRepository;
    private Context context;
    private MyModel myModel;
    private LiveData<MyModel> liveData = new MutableLiveData<>();

    public MyViewModel(Context context){
        this.context=context;
        myRepository = new MyRepository(context, this);
        LiveData<List<MyModel>> liveData = myRepository.getDataFromFirebase();
        //myRepository.saveDataToRoom(liveData);
    }

    public LiveData<MyModel> getQuestionLiveData(int position){
//        if (myRepository.myAppDatabase==null){
//            return liveData;
//        }else{
//            liveData = myRepository.getQuestionDetails(position);
//            return liveData;
//        }
        return myRepository.getQuestionDetails(position);
    }
}
