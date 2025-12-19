package com.example.notes;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.example.notes.notes.CreateNoteActivity;
import com.example.notes.search.SearchActivity;
import com.example.notes.onboarding.WelcomeActivity;
import com.example.notes.calendar.CalendarActivity;

public class BottomNavHelper {

    public static void setupBottomNav(Activity activity) {

        ImageView navNotes = activity.findViewById(R.id.navNotes);
        ImageView navCalendar = activity.findViewById(R.id.navCalendar);
        ImageView navSearch = activity.findViewById(R.id.navSearch);
        ImageView navCreate = activity.findViewById(R.id.navCreate);

        if (navNotes == null) return;

        navNotes.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, WelcomeActivity.class)));

        navCalendar.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, CalendarActivity.class)));

        navSearch.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, SearchActivity.class)));

        navCreate.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, CreateNoteActivity.class)));
    }
}
