package com.mparkersimms.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class SettingsPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String username = preferences.getString("username", "Guest");


        TextView usernameTextView = findViewById(R.id.usernameEditText);
        TextView usernameDisplayView = findViewById(R.id.usernameDisplayTextView);
        usernameDisplayView.setText(username);


        findViewById(R.id.saveButton).setOnClickListener(v -> {
            String user = usernameTextView.getText().toString();
            editor.putString("username", user);

            editor.apply();

            usernameDisplayView.setText(user);
        });

    }
}