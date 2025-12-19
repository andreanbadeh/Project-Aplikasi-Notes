package com.example.notes.calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.BottomNavHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView noteResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        noteResult = findViewById(R.id.noteResult);

        BottomNavHelper.setupBottomNav(this);

        SharedPreferences prefs = getSharedPreferences("notes", MODE_PRIVATE);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            Map<String, ?> allNotes = prefs.getAll();

            ArrayList<String> titles = new ArrayList<>();
            ArrayList<String> contents = new ArrayList<>();

            for (Map.Entry<String, ?> entry : allNotes.entrySet()) {

                String dateKey = entry.getKey();
                String raw = entry.getValue().toString(); // "title||content"

                long timeMillis;

                try {
                    timeMillis = Long.parseLong(dateKey);
                } catch (Exception e) {
                    continue;
                }

                // konversi timestamp → tanggal note
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(timeMillis);

                int nYear = c.get(Calendar.YEAR);
                int nMonth = c.get(Calendar.MONTH);
                int nDay = c.get(Calendar.DAY_OF_MONTH);

                // cocok
                if (nYear == year && nMonth == month && nDay == dayOfMonth) {

                    String[] parts = raw.split("\\|\\|");

                    String title = parts.length > 0 ? parts[0] : "(Tanpa Judul)";
                    String content = parts.length > 1 ? parts[1] : "";

                    titles.add(title);
                    contents.add(content);
                }
            }

            // jika tidak ada catatan
            if (titles.isEmpty()) {
                noteResult.setText("Tidak ada catatan pada tanggal ini.");
                return;
            }

            // tampilkan semua catatan
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < titles.size(); i++) {
                output.append("Catatan ").append(i + 1).append(":\n");
                output.append("Judul: ").append(titles.get(i)).append("\n");
                output.append("Isi: ").append(contents.get(i)).append("\n\n");
            }

            noteResult.setText(output.toString());
        });
    }
}
