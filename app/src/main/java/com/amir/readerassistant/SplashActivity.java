package com.amir.readerassistant;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    Timer timer;
    LottieAnimationView animFrameSplash;
    TextView tvSplashTitle;
    ActivityOptions activityOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpUIViews();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(animFrameSplash, "sharedTransitionAnimationSplash");
                //pairs[1] = new Pair<View, String>(animFrameSplash, "sharedTransitionAnimationSplash");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                        startActivity(intent, activityOptions.toBundle());
                    }
                });



                timer.cancel();
                finish();
            }
        }, 6000, 1000);
    }

    private void setUpUIViews() {
        animFrameSplash = findViewById(R.id.animFrameSplash);
        tvSplashTitle = findViewById(R.id.tvSplashTitle);
    }
}