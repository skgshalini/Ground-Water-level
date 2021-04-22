 package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

 public class UserProfile extends AppCompatActivity {
    TextView fullName;
    TextInputLayout namefield;
     TextInputLayout phoneNofield;
     TextInputLayout emailfield;
     String user_name;
     String user_email;
     String user_phoneNo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        fullName =findViewById(R.id.full_name);
        namefield =findViewById(R.id.name_field);
        emailfield =findViewById(R.id.email_field);
        phoneNofield=findViewById(R.id.phoneNo_field);
        
        showAllUserData();
    }

     private void showAllUserData() {
        Intent intent=getIntent();
         user_name=intent.getStringExtra("name");
         user_phoneNo=intent.getStringExtra("phoneNo");
         user_email=intent.getStringExtra("email");

         fullName.setText(user_name);
         namefield.getEditText().setText(user_name);
         emailfield.getEditText().setText(user_email);
         phoneNofield.getEditText().setText(user_phoneNo);




     }

     public void logout(View view) {
         Intent homeIntent = new Intent(Intent.ACTION_MAIN);
         homeIntent.addCategory( Intent.CATEGORY_HOME );
         homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(homeIntent);

     }

     public void dataEntry(View view) {
         Intent intent = new Intent(getApplicationContext(), DataEntry.class);
         intent.putExtra("name",user_name);
         intent.putExtra("phoneNo",user_email);
         intent.putExtra("email",user_phoneNo);
         startActivity(intent);
     }

     public void viewdata(View view) {
         Intent intent = new Intent(getApplicationContext(), ViewData.class);

         startActivity(intent);

     }
 }