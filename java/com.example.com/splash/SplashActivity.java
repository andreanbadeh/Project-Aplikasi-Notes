package com.example.notes.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.MainActivity;
import com.example.notes.R;

public class SplashActivity extends AppCompatActivity {

    View screen1, screen2, screen3, screen4;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // ambil semua layout step
        screen1 = findViewById(R.id.screen1);
        screen2 = findViewById(R.id.screen2);
        screen3 = findViewById(R.id.screen3);
        screen4 = findViewById(R.id.screen4);

        startStep1(); // mulai
    }

    private void startStep1() {
        screen1.setVisibility(View.VISIBLE);

        handler.postDelayed(() -> {
            screen1.setVisibility(View.GONE);
            screen2.setVisibility(View.VISIBLE);
            startStep2();
        }, 800);
    }

    private void startStep2() {
        View circle = screen2.findViewById(R.id.circle);
        Animation fall = AnimationUtils.loadAnimation(this, R.anim.fall_down);
        circle.startAnimation(fall);

        fall.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                screen2.setVisibility(View.GONE);
                screen3.setVisibility(View.VISIBLE);
                startStep3();
            }
        });
    }

    private void startStep3() {
        // ⭕ HANYA bigCircle yang di-zoom (bukan seluruh screen3)
        View circle = screen3.findViewById(R.id.bigCircle);

        Animation expand = AnimationUtils.loadAnimation(this, R.anim.expand);
        circle.startAnimation(expand);

        expand.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                screen3.setVisibility(View.GONE);
                screen4.setVisibility(View.VISIBLE);
                startStep4();
            }
        });
    }

    private void startStep4() {
        View panel = screen4.findViewById(R.id.panel);
        Animation up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        panel.startAnimation(up);

        Button next = screen4.findViewById(R.id.btnNext);
        next.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        });
    }
}
