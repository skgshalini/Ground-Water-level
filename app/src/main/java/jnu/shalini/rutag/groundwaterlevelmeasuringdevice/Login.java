package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
   Button callSignUp;
   TextInputLayout phoneNo;
    TextInputLayout otp;
    Button verify;
    Button sendOTP;
    ProgressBar progressBar;
    ProgressBar progressBarVerify;
    String UserEnteredOTP;
    String BackendOTP;
    String phoneNoFromDB;
    String emailFromDB;
    String nameFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_screen);
        verify = findViewById(R.id.verify);
        phoneNo = findViewById(R.id.phoneNo);
        progressBar = findViewById(R.id.progressbar_sendOTP);
        otp = findViewById(R.id.otp);
        sendOTP = findViewById(R.id.sendOTP);
        progressBarVerify=findViewById(R.id.progressbar_verify);
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }


        });

    }


        public void userVerification (View v){
            isUser();
        }

    private void isUser() {
        String UserEnteredPhoneNo= phoneNo.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser=reference.orderByChild("phoneNo").equalTo(UserEnteredPhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                   phoneNo.setError(null);
                   phoneNo.setErrorEnabled(false );

                    phoneNoFromDB=snapshot.child(UserEnteredPhoneNo).child("phoneNo").getValue(String.class);
                    emailFromDB=snapshot.child(UserEnteredPhoneNo).child("email").getValue(String.class);
                    nameFromDB=snapshot.child(UserEnteredPhoneNo).child("name").getValue(String.class);

                    sendOTP.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    phoneNo.setVisibility(View.GONE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phoneNo.getEditText().getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            Login.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(Login.this,"Verification Completed",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                                    intent.putExtra("name",nameFromDB);
                                    intent.putExtra("phoneNo",phoneNoFromDB);
                                    intent.putExtra("email",emailFromDB);
                                    startActivity(intent);


                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    phoneNo.setVisibility(View.VISIBLE);
                                    sendOTP.setVisibility(View.VISIBLE);
                                    Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    progressBar.setVisibility(View.GONE);
                                    otp.setVisibility(View.VISIBLE);
                                    verify.setVisibility(View.VISIBLE);
                                    BackendOTP=s;
                                    //Toast.makeText(Login.this,s,Toast.LENGTH_SHORT).show();
                                }


                            }

                    );


                }
                else{
                    phoneNo.setError("No such User exist");
                    phoneNo.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void otpVerification(View view){
            UserEnteredOTP = otp.getEditText().getText().toString().trim();
            if(BackendOTP!=null){
                verify.setVisibility(View.GONE);
                progressBarVerify.setVisibility(View.VISIBLE);
                PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(BackendOTP,UserEnteredOTP );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        verify.setVisibility(View.VISIBLE);
                        progressBarVerify.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("phoneNo",phoneNoFromDB);
                            intent.putExtra("email",emailFromDB);
                            startActivity(intent);
                        }
                        else {

                            otp.setError("Wrong OTP");
                            otp.requestFocus();
                        }
                    }
                });
            }
           else{
                Toast.makeText(Login.this,"Check internet connection",Toast.LENGTH_SHORT).show();
            }




    }
}