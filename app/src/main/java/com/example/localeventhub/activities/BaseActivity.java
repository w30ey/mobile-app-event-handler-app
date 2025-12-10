package com.example.localeventhub.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("prefs", MODE_PRIVATE);
        String lang = prefs.getString("language", "en");
        super.attachBaseContext(updateBaseContextLocale(newBase, lang));
    }

    private Context updateBaseContextLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}