package com.preprepare.prepare.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Repository.MyRepository;

import java.util.List;

public class MyViewModel {

    private static final String TAG = "MyViewModel";

    public MyRepository myRepository;
    private Context context;

    public MyViewModel(Context context){
        this.context=context;
        myRepository = new MyRepository(context);
        LiveData<List<MyModel>> liveData = myRepository.getDataFromFirebase();
        //myRepository.saveDataToRoom(liveData);
    }
}
