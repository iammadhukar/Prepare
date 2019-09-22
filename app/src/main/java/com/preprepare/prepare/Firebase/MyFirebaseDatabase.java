package com.preprepare.prepare.Firebase;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Repository.MyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFirebaseDatabase {

    private static final String TAG = "MyFirebaseDatabase";

    private FirebaseFirestore mFirestore;
    private Map<String, Object> myData;
    private DatabaseReference mDatabase;
    private ArrayList<MyModel> questionList;
    private String [] answers;
    private String question;
    private MyRepository myRepository;
    MutableLiveData<List<MyModel>> myMutableData = new MutableLiveData<>();
    private Handler handler = new Handler();

    public MyFirebaseDatabase(MyRepository myRepository){
        this.myRepository = myRepository;
        questionList= new ArrayList();
        myData = new HashMap<>();
        answers = new String [5];
    }

    /*
    * This method is used to get question list from firestore database in form of live data
     */
    public LiveData<List<MyModel>> getQuestionListLiveData(){
        accessDatabase();
        return myMutableData;
    }

    /*
    * This method access firestore database to fetch question list and save it in questionList arraylist.
     */
    public void accessDatabase(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Prepare");
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("Question bank").document("General").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                int i=0;
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        myData = document.getData();
                        for(Map.Entry<String, Object> localData: myData.entrySet()){
                            i++;
                            question=localData.getKey();
                            int count = 0;
                            for (String value: (List<String>)localData.getValue() ){
//                                Log.d(TAG, String.valueOf(count));
                                answers[count]=value;
                                count = count+1;
                            }
                            questionList.add(new MyModel(i, question, answers[0], answers[1], answers[2], answers[3], answers[4]));
                            Log.d(TAG, "Size of list is from inside : "+questionList.size());

                        }
                    }
                }

//                Log.d(TAG, String.valueOf(i));
                Log.d(TAG, "Size of list is : "+questionList.size());
                Log.d(TAG, "Second Uid is : "+questionList.get(2).getUid());
                Log.d(TAG, "Second question is : "+questionList.get(2).getQuestion());
                setQuestionList(questionList);
                myMutableData.setValue(questionList);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myRepository.saveDataToRoom(questionList);
                    }
                });

//                myRepository.getQuestionDetails(1);
            }
        });
        Log.d(TAG, "Size of list outside is : "+questionList.size());

    }

    public List<MyModel> getQuestionList(){
        return questionList;
    }

    private void setQuestionList(ArrayList<MyModel> list){
        Log.d(TAG, "Size of list set is : "+questionList.size());
        questionList = list;
//        Log.d(TAG, "Size of list set is : "+questionList.size());
    }

}
