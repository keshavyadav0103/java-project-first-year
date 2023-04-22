    package com.example.sample1;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.ArrayList;

    public class dencrypt extends AppCompatActivity {
        private String text;
        private String Text;
        private TextView print;
        EditText editText;
        DatabaseReference dbtref;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dencrypt);
            Intent intent = getIntent();
            text = intent.getStringExtra("text");

            print = findViewById(R.id.dn);
            editText=findViewById(R.id.enterkey);
            dbtref= FirebaseDatabase.getInstance().getReference().child("dencrypted data");

            ImageView exportButton = findViewById(R.id.dde);
            exportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the encrypted text from your app's data source
                    String encryptedText = Text;

                    // Call the exportEncryptedText() method to save the encrypted text to a file
                    exportDencryptedText(encryptedText);
                }
            });
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
            Text = String.valueOf(letter);

            insertdencryptdata();
        }
        private void insertdencryptdata(){
            String dkey=editText.getText().toString();
            String value=text;
            dencryptdata Dencryptdata=new dencryptdata(dkey,value);
            dbtref.push().setValue(Dencryptdata);
            Toast.makeText(getApplicationContext(),"data stored",Toast.LENGTH_SHORT).show();
        }

        public void exportDencryptedText(String encryptedText) {
            // Create a file object
            File file = new File(getExternalFilesDir(null), "dencrypted.txt");
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


    }}
