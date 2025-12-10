package com.example.localeventhub.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

import com.example.localeventhub.R;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.action_settings);

        RadioGroup radioGroup = findViewById(R.id.radioGroupLanguage);
        RadioButton rbEnglish = findViewById(R.id.rbEnglish);
        RadioButton rbAmharic = findViewById(R.id.rbAmharic);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String currentLanguage = prefs.getString("language", "en");

        if ("am".equals(currentLanguage)) {
            rbAmharic.setChecked(true);
        } else {
            rbEnglish.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String lang;
            if (checkedId == R.id.rbAmharic) {
                lang = "am";
            } else {
                lang = "en";
            }
            SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor.putString("language", lang);
            editor.apply();

            // Restart the app to apply the new language
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            if (i != null) {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}