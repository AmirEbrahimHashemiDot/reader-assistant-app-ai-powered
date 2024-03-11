package com.amir.readerassistant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class MainActivity extends AppCompatActivity {

    TextView titleMainAct, deskMainAct, tvTitleChooseFile;
    LottieAnimationView animFrameMainAct;
    ImageView imgBtnChoosePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpUIViews();

    }

    private void setUpUIViews() {
        titleMainAct = findViewById(R.id.titleMainAct);
        deskMainAct = findViewById(R.id.deskMainAct);
        tvTitleChooseFile = findViewById(R.id.tvTitleChooseImage);
        animFrameMainAct = findViewById(R.id.animFrameMainAct);
        imgBtnChoosePdf = findViewById(R.id.imgBtnChoosePdfFile);
        animFrameMainAct.setRepeatCount(LottieDrawable.INFINITE);
    }
}