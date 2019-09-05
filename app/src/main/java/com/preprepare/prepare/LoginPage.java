package com.preprepare.prepare;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginPage extends AppCompatActivity {

    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        signIn = findViewById(R.id.SignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInClick();
            }
        });


    }

    private void onSignInClick(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        overridePendingTransition( R.anim.slid_in_up, R.anim.slid_out_up );
        finish();

    }
}
