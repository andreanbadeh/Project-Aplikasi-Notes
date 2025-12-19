package com.example.notes.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeAdapter extends ArrayAdapter<String> {

    private final Context context;

    public ArrayList<String> titles;
    public ArrayList<String> contents;
    public ArrayList<String> keys;

    private final ArrayList<String> fullTitles;
    private final ArrayList<String> fullContents;
    private final ArrayList<String> fullKeys;

    public HomeAdapter(Context context,
                       ArrayList<String> titles,
                       ArrayList<String> contents,
                       ArrayList<String> keys) {

        super(context, R.layout.item_note, titles);

        this.context = context;

        this.titles = titles;
        this.contents = contents;
        this.keys = keys;

        this.fullTitles = new ArrayList<>(titles);
        this.fullContents = new ArrayList<>(contents);
        this.fullKeys = new ArrayList<>(keys);
    }

    static class ViewHolder {
        TextView tvTitle, tvContent, tvSubtitle;  // ← urutan diperbaiki
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);

            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);
            holder.tvContent = convertView.findViewById(R.id.tvContent);   // isi di tengah
            holder.tvSubtitle = convertView.findViewById(R.id.tvSubtitle); // jam paling bawah

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String title = titles.get(position);
        String content = contents.get(position);

        long timestamp = 0;
        try {
            timestamp = Long.parseLong(keys.get(position));
        } catch (Exception ignored) {}

        // SET SESUAI URUTAN YANG KAMU MAU
        holder.tvTitle.setText(title);
        holder.tvContent.setText(content);               // isi di tengah
        holder.tvSubtitle.setText(formatTime(timestamp)); // jam di bawah isi

        return convertView;
    }

    private String formatTime(long timeMillis) {

        if (timeMillis == 0) return "";

        Calendar now = Calendar.getInstance();
        Calendar noteDate = Calendar.getInstance();
        noteDate.setTimeInMillis(timeMillis);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM", Locale.getDefault());

        if (isSameDay(now, noteDate)) {
            return timeFormat.format(new Date(timeMillis));
        }

        now.add(Calendar.DAY_OF_YEAR, -1);
        if (isSameDay(now, noteDate)) {
            return "Kemarin " + timeFormat.format(new Date(timeMillis));
        }

        return dateFormat.format(new Date(timeMillis));
    }

    private boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public void filter(String text) {
        titles.clear();
        contents.clear();
        keys.clear();

        if (text.isEmpty()) {
            titles.addAll(fullTitles);
            contents.addAll(fullContents);
            keys.addAll(fullKeys);
        } else {
            text = text.toLowerCase();

            for (int i = 0; i < fullTitles.size(); i++) {

                String t = fullTitles.get(i).toLowerCase();
                String c = fullContents.get(i).toLowerCase();

                if (t.contains(text) || c.contains(text)) {
                    titles.add(fullTitles.get(i));
                    contents.add(fullContents.get(i));
                    keys.add(fullKeys.get(i));
                }
            }
        }

        notifyDataSetChanged();
    }
}

