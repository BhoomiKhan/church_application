package com.cornicosia.android;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        final ImageView logo = findViewById(R.id.logo);
        final TextView title = findViewById(R.id.title);
        Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        logo.startAnimation(fadein);
        title.startAnimation(fadein);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                logo.startAnimation(fadeout);
                title.startAnimation(fadeout);
            }
        }, 50);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Menu.class);
                startActivity(i);
                overridePendingTransition(R.anim.signin_incoming_screen_right_to_mean_position, R.anim.signin_current_screen_move_mean_to_left);
                finish();
            }
        }, 1200);
    }
}