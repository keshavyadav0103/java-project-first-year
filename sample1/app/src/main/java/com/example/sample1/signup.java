package com.example.sample1;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.sample1.User;
//import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    public static final String TAG="TAG";
    EditText mFullName,mUserName,mPassword,mConfirmPassword;
    Button msignuo;

    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mFullName=findViewById(R.id.FullName);
        mUserName=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);
        mConfirmPassword=findViewById(R.id.confpassword);
        msignuo=findViewById(R.id.button5);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),homepage.class));
            finish();
        }


        msignuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=mUserName.getText().toString().trim();
                String name=mFullName.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String cpassword=mConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    mFullName.setError(" FullName required");
                    return;
                }
                 if (TextUtils.isEmpty(username)){
                     mUserName.setError(" Username required");
                     return;
                 }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError(" password  required");
                    return;
                }


                if (TextUtils.isEmpty(cpassword)){
                    mConfirmPassword.setError(" password required");
                    return;
                }
                if (password.length()<6){
                    mPassword.setError("Password must Be >=6 character");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fuser= fAuth.getCurrentUser();

                            String uid = fuser.getUid();
                            User newUser = new User(username,name,password,cpassword);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("users").child(fuser.getUid());

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                            fuser.updateProfile(profileUpdates);

                            usersRef.setValue(newUser);

                            /*DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
                            usersRef.child(uid).setValue(newUser);*/

                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "SIGNUP Succesfull", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Onfailure: Email Not Sent" + e.getMessage());
                                }
                            });

                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference =fstore.collection("user").document(userID);
                            Map<String,Object> user= new HashMap<>();
                            user.put("fname",name);
                            user.put("fuser",username);
                            user.put("fpass",password);
                            user.put("fcpass",cpassword);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSucces: user profile is created for "+userID);
                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onfaolure: "+e.toString());

                                }
                            }) ;
                            //startActivity(new Intent(getApplicationContext(),MyClass.class));

                            startActivity(new Intent(getApplicationContext(),homepage.class));
                        }
                        else{
                            Toast.makeText(signup.this, "ERROR"+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



    }
}