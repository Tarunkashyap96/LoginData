package com.example.logindata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    Button callSignUp, login_btn, Forgot;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //This Line will hide the status bar from the screen
        setContentView(R.layout.activity_dashboard);
        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.Go);



        login_btn.setOnClickListener(new View.OnClickListener() {

            private Boolean validateUsername() {

                String val = username.getEditText().getText().toString();

                if (val.isEmpty()) {
                    username.setError("Field cannot be empty");
                    return false;
                }  else {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validatePassword() {
                String val = password.getEditText().getText().toString();

                if (val.isEmpty()) {
                    password.setError("Field cannot be empty");
                    return false;
                } else {
                    password.setError(null);
                    password.setErrorEnabled(false);
                    return true;
                }
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Wait for few seconds", Toast.LENGTH_SHORT).show();
                if (!validateUsername() | !validatePassword()) {
                    return;
                } else {

                    final String userEnteredUsername = username.getEditText().getText().toString().trim();
                    final String userEnteredPassword = password.getEditText().getText().toString().trim();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

                    Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                username.setError(null);
                                username.setErrorEnabled(false);

                                String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                                if (passwordFromDB.equals(userEnteredPassword)) {

                                    String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                                    String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                                    String phoneFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                                    String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("username", usernameFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("phone", phoneFromDB);
                                    intent.putExtra("password", passwordFromDB);

                                    startActivity(intent);
                                } else {
                                    password.setError("Wrong Password");
                                }

                            } else {
                                username.setError("No such User exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
                //  Intent intent = new Intent(Dashboard.this,UserProfile.class);
                  //startActivity(intent);

        });
        callSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }

}
