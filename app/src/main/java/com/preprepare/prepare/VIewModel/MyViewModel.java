package com.preprepare.prepare.VIewModel;

import android.content.Context;

import com.preprepare.prepare.Repository.MyRepository;

public class MyViewModel {

    private static final String TAG = "MyViewModel";

    private MyRepository myRepository;
    private Context context;

    public MyViewModel(Context context){
        this.context=context;
        myRepository = new MyRepository(context);
        myRepository.getDataFromFirebase();
//        myRepository.saveDataToRoom();
    }
}
