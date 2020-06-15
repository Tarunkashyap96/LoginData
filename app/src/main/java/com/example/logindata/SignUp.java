package com.example.logindata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    Button regBtn , regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.Go);


        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view) {

                   rootNode = FirebaseDatabase.getInstance();
                   reference = rootNode.getReference("User");

                    String name = regName.getEditText().getText().toString();
                    String username = regUsername.getEditText().getText().toString();
                    String email = regEmail.getEditText().getText().toString();
                    String phone = regPhone.getEditText().getText().toString();
                    String password = regPassword.getEditText().getText().toString();
                    UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password);
                    reference.child(username).setValue(helperClass);
                    Toast.makeText(SignUp.this, "Thank you For Create Account ", Toast.LENGTH_SHORT).show();

                }

        });

              regBtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(SignUp.this,Dashboard.class);
                      startActivity(intent);

                  }
              });

    }




}
