package com.example.notes.onboarding;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.home.HomeActivity;
import com.example.notes.utils.LocaleHelper;

public class LanguageActivity extends AppCompatActivity {

    // 6 BAHASA (DITAMBAH THAILAND)
    private final String[] langNames = {
            "Indonesia",
            "Inggris",
            "Jepang",
            "China",
            "Rusia",
            "Thailand"       // Thailand
    };

    private final String[] langCodes = {
            "id",
            "en",
            "ja",
            "zh",
            "ru",
            "th"        // Kode bahasa Thai
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocaleHelper.loadLocale(this);
        setContentView(R.layout.activity_language);

        Button change = findViewById(R.id.btnChangeLang);
        Button start = findViewById(R.id.btnStart);

        change.setOnClickListener(v -> showLanguagePicker());

        start.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void showLanguagePicker() {
        new AlertDialog.Builder(this)
                .setTitle("Pilih Bahasa")
                .setItems(langNames, (dialog, which) -> {
                    String lang = langCodes[which];
                    LocaleHelper.setLocale(this, lang);

                    recreate();
                })
                .show();
    }
}
