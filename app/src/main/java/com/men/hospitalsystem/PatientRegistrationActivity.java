package com.men.hospitalsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.men.hospitalsystem.Utility.NetworkChangeListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientRegistrationActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    //variables
    private TextView regPageQuestion;
    private TextInputEditText registrationFullName, registrationIdNumber,
            loginEmail, loginPassword;
    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;


    private Button regButton;


    //image variable
    private CircleImageView profileImage;
    FloatingActionButton fab;
    private Uri resultUri;

    //firebase variable
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private FirebaseDatabase database;
    FirebaseStorage storage;


    //progressbar variable
    private ProgressDialog loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        regPageQuestion = findViewById(R.id.regPageQuestion);
        regPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registrationFullName = findViewById(R.id.registrationFullName);
        registrationIdNumber = findViewById(R.id.registrationIdNumber);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        regButton = findViewById(R.id.regButton);
        profileImage = findViewById(R.id.ProfileImage);
        fab = findViewById(R.id.floatingActionButton);
        radioGroup = findViewById(R.id.radio_group);


        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(PatientRegistrationActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    public void FirstClick(View view) {

        if (!validateGender() | !validateName() | !validateId() | !validateEmail() | !validatePassword()) {
            return;
        }
        if (resultUri == null) {
            Toast.makeText(PatientRegistrationActivity.this, "Profile image is required",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        selectedRadioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());


        String gender = selectedRadioButton.getText().toString();
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        String fullName = registrationFullName.getText().toString();
        String idNumber = registrationIdNumber.getText().toString();



        Intent intent = new Intent(PatientRegistrationActivity.this, OtpSendActivity.class);
        //Pass all fields to the next activity
        intent.putExtra("selectedRadioButton", gender);
        intent.putExtra("loginEmail", email);
        intent.putExtra("loginPassword", password);
        intent.putExtra("registrationFullName", fullName);
        intent.putExtra("registrationIdNumber", idNumber);
        startActivity(intent);
    }



    private Boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private Boolean validateName() {
        String val = registrationFullName.getText().toString();

        if (val.isEmpty()) {
            registrationFullName.setError("Field cannot be empty");
            return false;
        } else {
            registrationFullName.setError(null);
            return true;
        }
    }

    private Boolean validateId() {
        String val = registrationIdNumber.getText().toString();

        if (val.isEmpty()) {
            registrationIdNumber.setError("Field cannot be empty");
            return false;
        } else {
            registrationIdNumber.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = loginEmail.getText().toString();
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (val.isEmpty()) {
            loginEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            loginEmail.setError("Invalid email address");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            loginPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            loginPassword.setError("Password is too weak");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }



    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void unregisterReceiver(NetworkChangeListener networkChangeListener) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resultUri = data.getData();
        profileImage.setImageURI(resultUri);

        final StorageReference reference=storage.getReference()
                .child("image");

        reference.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        database.getReference("users").child("image").setValue(resultUri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Profile image uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });


    }
}