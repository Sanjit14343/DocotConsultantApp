package com.men.hospitalsystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);



        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.aboutus_icon)
                .setDescription(" Our project is on “Hospital Management System” application. \n" +
                        "\n" +
                        "In this application we are giving detailed information about the hospital and its operations.\n")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("CONNECT WITH US!")
                .addEmail("Your mail id ")
                .addWebsite("Your website/")
                .addYoutube("https://www.youtube.com/channel/UCvEXhV8UN2zwQpgKmowvjBQ")   //Enter your youtube link here (replace with my channel link)
                .addPlayStore("com.men.hospitalsystem")   //Replace all this with your package name
                .addInstagram("netdigifuture")    //Your instagram id
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }
    private Element createCopyright()
    {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by DocCare Team", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        // copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setIconDrawable(R.drawable.ic_aboutus);
        copyright.setAutoApplyIconTint(true);
        copyright.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyright.setIconNightTint(android.R.color.white);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aboutus.this,copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}