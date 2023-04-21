package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class encrypt extends AppCompatActivity {
    TextView Key;
    ImageView copykey;
    private String text;
    private TextView print;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        print = findViewById(R.id.en);
        Key=findViewById(R.id.gkey);
        copykey=findViewById(R.id.copy_key);
        copykey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Text",Key.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                copykey.setImageDrawable(getResources().getDrawable(R.drawable.baseline_done_24));
                Toast.makeText(getApplicationContext(),"Text copied to cliboard",Toast.LENGTH_SHORT).show();
            }
        });
    }

    ArrayList<Character> shuffledList;
    ArrayList<Character> list;
    private void shuffleList() {
        list = new ArrayList<>();
        for(int i = 32; i < 127; i++){
            list.add((char)i);
        }
       shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList);
    }

    public void starte(View view) {
        shuffleList();
        char[] letter = text.toCharArray();
        for(int i = 0; i < letter.length; i++){
            for(int j = 0; j < list.size(); j++){
                if(letter[i] == list.get(j)){
                    letter[i] = shuffledList.get(j);
                    break;
                }
            }
        }
        text = String.valueOf(letter);
        print.setText(text);
    }

    public void getkey(View view) {
        TextView out = findViewById(R.id.gkey);
        String skey = "";
        for(Object x: shuffledList){
            skey += x;
        }
        out.setText(skey);
    }
}
