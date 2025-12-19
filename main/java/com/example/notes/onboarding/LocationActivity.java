package com.example.notes.onboarding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.utils.LocaleHelper;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    TextView txtCountry, txtFlag;
    Button btnNext;

    private static final int LOCATION_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocaleHelper.loadLocale(this);

        setContentView(R.layout.activity_location);

        txtCountry = findViewById(R.id.txtCountry);
        txtFlag = findViewById(R.id.txtFlag);
        btnNext = findViewById(R.id.btnNext);

        requestPermission();

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(this, LanguageActivity.class));
        });
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST
            );

        } else {
            detectCountry();
        }
    }

    private void detectCountry() {
        try {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        1
                );

                if (!list.isEmpty()) {

                    String countryName = list.get(0).getCountryName();
                    String countryCode = list.get(0).getCountryCode();

                    String flag = countryToFlag(countryCode);

                    // ====== AMBIL KOTA ======
                    String cityName = list.get(0).getLocality();            // nama kota
                    if (cityName == null)
                        cityName = list.get(0).getSubAdminArea();          // fallback kota besar
                    if (cityName == null)
                        cityName = "-";

                    // bendera di bagian atas
                    txtFlag.setText(flag);

                    // negara + kota
                    txtCountry.setText(flag + " " + countryName + ", " + cityName);
                }

            } else {
                txtCountry.setText("Tidak dapat mendeteksi lokasi");
            }

        } catch (Exception e) {
            txtCountry.setText("Lokasi error");
        }
    }

    private String countryToFlag(String code) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        StringBuilder emoji = new StringBuilder();
        for (char c : code.toUpperCase().toCharArray()) {
            emoji.append(Character.toChars(flagOffset + (c - asciiOffset)));
        }
        return emoji.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                detectCountry();

            } else {
                txtCountry.setText("Izin lokasi ditolak");
            }
        }
    }
}
