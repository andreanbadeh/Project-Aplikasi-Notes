package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// TAMBAHKAN IMPORT INI
import com.example.notes.onboarding.WelcomeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ARAHKAN KE CLASS YANG SUDAH DI-IMPORT
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }
}
