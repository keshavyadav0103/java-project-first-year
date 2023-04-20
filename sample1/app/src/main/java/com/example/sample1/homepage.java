package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class homepage extends AppCompatActivity {
    ImageButton imageButton;
    Button encrypt;
    Button dencrypt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        imageButton =findViewById(R.id.imageView2);
        encrypt=findViewById(R.id.Encrypt);
        dencrypt=findViewById(R.id.Dencrypt);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),setting.class);
                startActivity(intent);
            }
        });
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),encrypt.class);
                startActivity(intent);
            }
        });
        dencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),dencrypt.class);
                startActivity(intent);
            }
        });




    }
}