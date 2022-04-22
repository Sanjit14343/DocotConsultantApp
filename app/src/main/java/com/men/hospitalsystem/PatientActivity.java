package com.men.hospitalsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button doctordetails;
    RecyclerView recyclerView;
    

    Button btnLogout;
    FirebaseAuth mAuth;

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        drawerLayout=findViewById(R.id.drawerlayout);
        recyclerView= findViewById(R.id.rv);
        navigationView=findViewById(R.id.navigationview);
        toolbar=findViewById(R.id.toolbar);
        doctordetails=findViewById(R.id.doctorDetails);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        doctordetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(PatientActivity.this, DoctorDetailsActivity.class);
                startActivity(intent);
            }
        });


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.home_menu);


        btnLogout=findViewById(R.id.btnLogout);
        mAuth= FirebaseAuth.getInstance();
        

        btnLogout.setOnClickListener(view->{
            mAuth.signOut();
            startActivity(new Intent(PatientActivity.this, MainActivity.class));
            Animatoo.animateFade(PatientActivity.this);
        });
    }


    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
            finishAffinity();

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_menu:
            break;
            case R.id.dashboard_menu:
            Intent intent = new Intent(PatientActivity.this, SelectRegistrationActivity.class);
            startActivity(intent);
            Animatoo.animateSwipeLeft(PatientActivity.this);
            break;

            case R.id.aboutus_menu:{
                Intent intent3 = new Intent(PatientActivity.this , aboutus.class);
                startActivity(intent3);
                }
            break;

            case  R.id.share_menu:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                String shareSub = "Try now";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            break;

            case R.id.logout_menu:
                Intent intent1 = new Intent(PatientActivity.this, MainActivity.class);
                startActivity(intent1);
                Animatoo.animateFade(PatientActivity.this);
                break;



        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}

