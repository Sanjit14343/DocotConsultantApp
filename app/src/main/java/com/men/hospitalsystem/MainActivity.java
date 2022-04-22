package com.men.hospitalsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.men.hospitalsystem.Utility.NetworkChangeListener;

import java.util.concurrent.atomic.LongAccumulator;

public class MainActivity extends AppCompatActivity {

    //check internet connection
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    //on back pressed do you want to exit screen
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finishAffinity();
                    }
                }


        );
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog= builder.create();
        builder.show();
    }


    private TextInputEditText loginPhone, loginPassword;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button loginButton = findViewById(R.id.loginButton);
        Button forgotPassword = findViewById(R.id.forgotPassword);
        loginPhone = findViewById(R.id.phone);
        loginPassword = findViewById(R.id.loginPassword);

        mAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String _phone = loginPhone.getText().toString().trim();
                final String _password = loginPassword.getText().toString().trim();

               if(TextUtils.isEmpty(_phone)){
                   loginPhone.setError("Email cannot be empty");
                   loginPhone.requestFocus();
               }else if( TextUtils.isEmpty(_password)){
                   loginPassword.setError("Password cannot be empty");
                   loginPassword.requestFocus();
               }else{
                   Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phone").equalTo(_phone);

                   checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.exists()){
                               loginPhone.setError(null);

                               String systemPassword= snapshot.child(_phone).child("password").getValue(String.class);
                               if(systemPassword.equals(_password)){
                                   loginPassword.setError(null);

                                   Toast.makeText(MainActivity.this, "Patient logged in successfully", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(MainActivity.this, PatientActivity.class));
                                   Animatoo.animateZoom(MainActivity.this);

                               }
                               else{
                                   Toast.makeText(MainActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                               }
                           }
                           else{
                               Toast.makeText(MainActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                           Toast.makeText(MainActivity.this, "No such user exist!", Toast.LENGTH_SHORT).show();

                       }
                   });

               }
            }
        });


        //forget password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your Email to received Reset link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // EXTRACT THE EMAIL AND SEND RESEND LINK
                                String mail= resetMail.getText().toString();
                                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Reset link sent to your Email", Toast.LENGTH_SHORT).show();
                                    }
                                }). addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Error ! Reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                );
                passwordResetDialog.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //CLOSE THE DIALOG
                            }
                        }
                );

                passwordResetDialog.create().show();

            }
        }); //

    }

    public void FirstClick(View view) {
        startActivity(new Intent(this, SelectRegistrationActivity.class));
        Animatoo.animateZoom(this);
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void unregisterReceiver(NetworkChangeListener networkChangeListener) {
    }

}