package com.preprepare.prepare;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LoginPage extends AppCompatActivity {

    private static final String TAG = "LoginPage";
    private static final int RC_SIGN_IN = 101;

    private Button signIn, generateOtp;
    private EditText phoneNumberEditText, otpEditText;
    private String number;
    private FirebaseAuth mAuth;
    private String codeSent, otpEntered;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signIn = findViewById(R.id.SignIn);
        generateOtp = findViewById(R.id.GenerateOtp);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        otpEditText = findViewById(R.id.otp);

        otpEditText.setVisibility(View.GONE);
        signIn.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

    }

    private void goToDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        overridePendingTransition( R.anim.slid_in_up, R.anim.slid_out_up );
        finish();

    }

    public void onGenerateOtpClicked(View view){

        phoneNumberEditText = findViewById(R.id.phoneNumber);

        number = phoneNumberEditText.getText().toString();

        if (number.isEmpty()){
            phoneNumberEditText.setError("Phone number is required");
            phoneNumberEditText.requestFocus();
            return;
        }

        if (number.length()<10){
            phoneNumberEditText.setError("Please enter correct phone number");
            phoneNumberEditText.requestFocus();
            return;
        }



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG,"Verification Completed");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG,"failed");
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG,"Code sent");
                codeSent = s;
                phoneNumberEditText.setVisibility(View.GONE);
                generateOtp.setVisibility(View.GONE);

                otpEditText.setVisibility(View.VISIBLE);
                signIn.setVisibility(View.VISIBLE);
                otpEditText.requestFocus();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.d(TAG,"Time out");
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+number,        // Phone number to verify
                10,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    public void onSignInClick(View view){

        otpEntered = otpEditText.getText().toString();
        verifyOtpCode();
    }

    private void verifyOtpCode(){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, otpEntered);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "Login Successful...", Toast.LENGTH_LONG).show();
                            goToDashboard();
//                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "Incorrect Otp...", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
