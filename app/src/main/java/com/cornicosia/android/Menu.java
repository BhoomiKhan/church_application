package com.cornicosia.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Menu extends AppCompatActivity {
    private LinearLayout home, notifications,contact, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        home=(LinearLayout) findViewById(R.id.home);
        notifications=(LinearLayout) findViewById(R.id.notifications);
        contact=(LinearLayout) findViewById(R.id.contact);
        about=(LinearLayout) findViewById(R.id.about);
//        social_media=(LinearLayout) findViewById(R.id.social_media);

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,MainActivity.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Menu.this,About.class));

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,ContactUs.class));
            }
        });
    }
}