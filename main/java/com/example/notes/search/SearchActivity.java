package com.example.notes.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.BottomNavHelper;
import com.example.notes.R;
import com.example.notes.notes.CreateNoteActivity;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    EditText searchInput;
    ListView listSearch;

    ArrayList<String> titles = new ArrayList<>();      // list judul
    ArrayList<String> keys = new ArrayList<>();        // list KEY (timestamp)
    ArrayList<String> filteredTitles = new ArrayList<>();
    ArrayList<String> filteredKeys = new ArrayList<>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavHelper.setupBottomNav(this);

        searchInput = findViewById(R.id.searchField);
        listSearch = findViewById(R.id.listResult);

        loadNotes();
        setupSearch();
    }

    private void loadNotes() {
        SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);
        Map<String, ?> savedNotes = prefs.getAll();

        titles.clear();
        keys.clear();

        for (String key : savedNotes.keySet()) {
            String raw = (String) savedNotes.get(key);

            if (raw != null) {
                String[] parts = raw.split("\\|\\|");

                String title = parts.length > 0 ? parts[0] : "";

                titles.add(title);
                keys.add(key);
            }
        }

        filteredTitles.clear();
        filteredKeys.clear();

        filteredTitles.addAll(titles);
        filteredKeys.addAll(keys);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                filteredTitles);

        listSearch.setAdapter(adapter);

        listSearch.setOnItemClickListener((parent, view, position, id) -> {
            String selectedKey = filteredKeys.get(position);

            Intent intent = new Intent(SearchActivity.this, CreateNoteActivity.class);
            intent.putExtra("dateKey", selectedKey);
            startActivity(intent);
        });
    }

    private void setupSearch() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterNotes(String query) {
        filteredTitles.clear();
        filteredKeys.clear();

        for (int i = 0; i < titles.size(); i++) {

            String t = titles.get(i);

            if (t.toLowerCase().contains(query.toLowerCase())) {
                filteredTitles.add(t);
                filteredKeys.add(keys.get(i));
            }
        }

        adapter.notifyDataSetChanged();
    }
}
