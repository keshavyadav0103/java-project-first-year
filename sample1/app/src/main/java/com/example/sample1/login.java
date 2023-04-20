package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText memail,mpassword;
    TextView msingnupbtn;
    Button mloginbtn;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memail=findViewById(R.id.luser);
        mpassword=findViewById(R.id.lpassword);
        mloginbtn=findViewById(R.id.button2);

        msingnupbtn=findViewById(R.id.signupRedirectText);
        fAuth=FirebaseAuth.getInstance();


        msingnupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });


        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mpassword.setError("password is required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent5 = new Intent(getApplicationContext(),homepage.class);
                            startActivity(intent5);
                            //Toast.makeText(getApplicationContext(), "login succes", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),homepage.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }
}