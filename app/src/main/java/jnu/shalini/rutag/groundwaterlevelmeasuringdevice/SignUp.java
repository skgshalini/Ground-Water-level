package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
   public TextInputLayout regName;
   public  TextInputLayout regEmail;
   public TextInputLayout  regPhoneNo;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        regName= findViewById(R.id.name);
        regEmail= findViewById(R.id.email);
        regPhoneNo= findViewById(R.id.phoneNo);
    }

    public void registerUser(View view) {

        String name=regName.getEditText().getText().toString();
        String email=regEmail.getEditText().getText().toString();
        String phoneNo=regPhoneNo.getEditText().getText().toString();
        rootNode= FirebaseDatabase.getInstance();
        reference =rootNode.getReference("Users");
        Uploading uploading =new Uploading(name,email,phoneNo);
        reference.child(phoneNo).setValue(uploading);
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);

    }
}