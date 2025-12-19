package com.example.notes.utils;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslateHelper {

    public interface TranslateCallback {
        void onTranslated(String result);
    }

    public static void translate(String text, String targetLang, TranslateCallback callback) {

        if (text == null || text.trim().isEmpty()) {
            callback.onTranslated(text);
            return;
        }

        String apiUrl = "https://api.mymemory.translated.net/get?q="
                + text.replace(" ", "%20")
                + "&langpair=" + "id" + "|" + targetLang;

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );

                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String json = result.toString();

                    // ambil hasil translate
                    int idx = json.indexOf("\"translatedText\":\"");
                    if (idx != -1) {
                        int start = idx + "\"translatedText\":\"".length();
                        int end = json.indexOf("\"", start);
                        return json.substring(start, end);
                    }

                } catch (Exception e) {
                    return text;
                }
                return text;
            }

            @Override
            protected void onPostExecute(String s) {
                callback.onTranslated(s);
            }
        }.execute();
    }
}
