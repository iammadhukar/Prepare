package com.preprepare.prepare;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bpsc extends AppCompatActivity {

    Button start_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpsc);

        start_test = findViewById(R.id.start_test);
    }

    public void onStartTestClick(View view){
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }


}
