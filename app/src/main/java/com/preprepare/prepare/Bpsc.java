package com.preprepare.prepare;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.preprepare.prepare.ViewModel.MyViewModel;

public class Bpsc extends AppCompatActivity {

    Button start_test;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpsc);

        start_test = findViewById(R.id.start_test);

//        myViewModel = new MyViewModel(this);
    }

    public void onStartTestClick(View view){
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }


}
