package com.amir.readerassistant;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.ViewCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class ExploreActivity extends AppCompatActivity {

    LottieAnimationView searchActMainAnimFrame;
    Button btnSearch;
    MaterialAutoCompleteTextView edtSearchBar;

    public static int animState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        setUpViews();

    }

    private void setUpViews() {
        searchActMainAnimFrame = findViewById(R.id.searchActMainAnimFrame);
        btnSearch = findViewById(R.id.btnSearch);
        edtSearchBar = findViewById(R.id.edtSearchBar);
        searchActMainAnimFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchActMainAnimFrame.playAnimation();
            }
        });

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.search_clicked_sound_effect);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchActMainAnimFrame.playAnimation();
                mediaPlayer.start();

                if (animState == 1) {

                    ObjectAnimator animatorLottieFrame = ObjectAnimator.ofFloat(searchActMainAnimFrame, "translationY", 0, -searchActMainAnimFrame.getHeight());
                    animatorLottieFrame.setDuration(1000);
                    animatorLottieFrame.start();

                    WindowInsets insets = getWindow().getDecorView().getRootWindowInsets();
                    int statusBarHeight = insets.getSystemWindowInsetTop();

                    ObjectAnimator animatorEdtSearchBar = ObjectAnimator.ofFloat(edtSearchBar, "translationY", -edtSearchBar.getY(), statusBarHeight - edtSearchBar.getY());
                    animatorEdtSearchBar.setDuration(1000);
                    animatorEdtSearchBar.start();


                    ObjectAnimator animatorBtnSearch = ObjectAnimator.ofFloat(btnSearch, "translationY", -btnSearch.getY(), statusBarHeight - btnSearch.getY());
                    animatorBtnSearch.setDuration(1000);
                    animatorBtnSearch.start();

                    animState = 0;
                } else if (animState == 0) {

                    ObjectAnimator animatorLottieFrameComeBack = ObjectAnimator.ofFloat(searchActMainAnimFrame, "translationY", -searchActMainAnimFrame.getHeight(), 0);
                    animatorLottieFrameComeBack.setDuration(1000);
                    animatorLottieFrameComeBack.start();

                    ObjectAnimator animatorSearchBarComeBack = ObjectAnimator.ofFloat(edtSearchBar, "translationY", -edtSearchBar.getY(), 0.5f);
                    animatorSearchBarComeBack.setDuration(1000);
                    animatorSearchBarComeBack.start();

                    ObjectAnimator animatorBtnSearchComeBack = ObjectAnimator.ofFloat(btnSearch, "translationY", -edtSearchBar.getY(), 0.5f);
                    animatorBtnSearchComeBack.setDuration(1000);
                    animatorBtnSearchComeBack.start();

                    animState = 1;
                }
            }
        });
    }
}