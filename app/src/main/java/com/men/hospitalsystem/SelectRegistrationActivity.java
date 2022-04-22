package com.men.hospitalsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class SelectRegistrationActivity extends AppCompatActivity {

    private TextView back;
    private Button doctorReg, patientReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_registration);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(SelectRegistrationActivity.this);

            }
        });

        patientReg = findViewById(R.id.patientReg);
        doctorReg = findViewById(R.id.doctorReg);

        patientReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SelectRegistrationActivity.this, PatientRegistrationActivity.class);
                startActivity(intent);
                Animatoo.animateShrink(SelectRegistrationActivity.this);

            }
        });

        doctorReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SelectRegistrationActivity.this, DoctorRegistrationActivity.class);
                startActivity(intent);
                Animatoo.animateShrink(SelectRegistrationActivity.this);

            }
        });
    }
}