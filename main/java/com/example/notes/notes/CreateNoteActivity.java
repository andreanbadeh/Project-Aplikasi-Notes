package com.example.notes.notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.utils.LocaleHelper;   // ← FIX IMPORT

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    EditText inputTitle, inputContent;
    Button btnSave;

    private String dateKey = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LOAD BAHASA
        LocaleHelper.loadLocale(this);

        setContentView(R.layout.activity_create_note);

        inputTitle = findViewById(R.id.inputTitle);
        inputContent = findViewById(R.id.inputContent);
        btnSave = findViewById(R.id.btnSave);

        // TOOLBAR
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // AMBIL DATE KEY DARI INTENT
        dateKey = getIntent().getStringExtra("dateKey");

        // Jika edit: load data lama
        if (dateKey != null) {
            SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);
            String raw = prefs.getString(dateKey, null);

            if (raw != null) {
                String[] parts = raw.split("\\|\\|");

                if (parts.length > 0) inputTitle.setText(parts[0]);
                if (parts.length > 1) inputContent.setText(parts[1]);
            }
        }

        btnSave.setOnClickListener(v -> saveNote());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Tombol back
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        // Tombol delete
        if (item.getItemId() == R.id.menu_delete) {
            if (dateKey == null) {
                Toast.makeText(this, getString(R.string.note_not_saved), Toast.LENGTH_SHORT).show();
                return true;
            }
            showDeleteDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_note))
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> deleteNote())
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void deleteNote() {
        SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.remove(dateKey);
        editor.apply();

        Toast.makeText(this, getString(R.string.note_deleted), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveNote() {
        String title = inputTitle.getText().toString().trim();
        String content = inputContent.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_note_warning), Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (dateKey == null) {
            dateKey = String.valueOf(System.currentTimeMillis());
        }

        editor.putString(dateKey, title + "||" + content);
        editor.apply();

        Toast.makeText(this, getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
        finish();
    }
}
