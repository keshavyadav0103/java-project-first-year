package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class encrypt extends AppCompatActivity {
    TextView Key;
    private static final String  filename ="Encrypt.txt";
    ImageView copykey;
    private String text;
    private String Text;
    private TextView print;

    DatabaseReference dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        print = findViewById(R.id.en);
        Key=findViewById(R.id.gkey);

        dbref= FirebaseDatabase.getInstance().getReference().child("encrypted data");
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

        ImageView exportButton = findViewById(R.id.ed);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the encrypted text from your app's data source
                String encryptedText = Text;

                // Call the exportEncryptedText() method to save the encrypted text to a file
                exportEncryptedText(encryptedText);
            }
        });



    }

    ArrayList<Character> shuffledList;
    ArrayList<Character> list;
    String skey = "";
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
        Text = String.valueOf(letter);
        Toast.makeText(getApplicationContext(),"Encryption done",Toast.LENGTH_SHORT).show();


    }
    public void getkey(View view) {
        TextView out = findViewById(R.id.gkey);

        for(Object x: shuffledList){
            skey += x;
        }
        out.setText(skey);

        insertencryptdata();
    }
    private void insertencryptdata(){
        String key=skey;
        String value=text;
        encryptdata Encryptdata=new encryptdata(key,value);
        dbref.push().setValue(Encryptdata);
        Toast.makeText(getApplicationContext(),"data stored",Toast.LENGTH_SHORT).show();

    }


    public void exportEncryptedText(String encryptedText) {
        // Create a file object
        File file = new File(getExternalFilesDir(null), "encrypted.txt");
        FileOutputStream fos = null;

        try {
            // Open a file output stream
            fos = new FileOutputStream(file);

            // Write the encrypted text to the file
            fos.write(encryptedText.getBytes());

            // Close the file output stream
            fos.close();

            // Display a message to the user indicating that the file was saved successfully
            Toast.makeText(this, " " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Display an error message to the user
            Toast.makeText(this, "Error saving encrypted text", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }




}