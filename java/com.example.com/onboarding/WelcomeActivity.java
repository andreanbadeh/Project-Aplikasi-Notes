package com.example.notes.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.utils.LocaleHelper;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocaleHelper.loadLocale(this);

        setContentView(R.layout.activity_welcome);

        Button btn = findViewById(R.id.btnNext);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });
    }
}
