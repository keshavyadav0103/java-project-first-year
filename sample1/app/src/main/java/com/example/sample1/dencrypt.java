package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class dencrypt extends AppCompatActivity {
    private String text;
    private TextView print;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dencrypt);
        Intent intent = getIntent();
        text = intent.getStringExtra("text");

        print = findViewById(R.id.dn);
        editText=findViewById(R.id.enterkey);
    }


    public void startdn(View view){
        String key= editText.getText().toString();
        char[] shuffledList = key.toCharArray();
        ArrayList<Character> list = new ArrayList<>();
        for(int i = 32; i < 127; i++){
            list.add((char)i);
        }
        char[] letter = text.toCharArray();
        for(int i=0;i<letter.length;i++)
        {
            for(int j=0;j<shuffledList.length;j++)
            {
                if(letter[i]==shuffledList[j])
                {
                    letter[i]=list.get(j);
                    break;
                }
            }
        }
        text = String.valueOf(letter);
        print.setText(text);
    }
}
