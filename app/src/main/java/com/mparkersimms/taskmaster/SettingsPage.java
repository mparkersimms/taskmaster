package com.mparkersimms.taskmaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SettingsPage extends AppCompatActivity {
    String TAG = "msimms.settingsPage";
    List<Team> teams = new ArrayList<>();
    Handler mainThreadHandler;
    Spinner spinner;
    ArrayAdapter<Team> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

//        ========== handler for updates from the DB to the page ==============

        mainThreadHandler = new Handler(this.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "------------------------------handleMessages: hit the handler");
                if (msg.what == 3) {
                    StringJoiner sj = new StringJoiner(" , ");
                    for(Team team : teams){
                        sj.add(team.getName());
                    }
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
        };

//             ========= creates the spinner to hold the names of the teams===============

        spinner = findViewById(R.id.settingsTeamSpinner);
        spinnerAdapter = new ArrayAdapter<>(SettingsPage.this, android.R.layout.simple_spinner_dropdown_item, teams);
        spinner.setAdapter(spinnerAdapter);

//          ============ asking DynamoDB through Amplify for the list of Teams to display in the spinner ===========

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    Log.i(TAG, "onCreate: " + response);
                    for (Team team : response.getData()) {
                        teams.add(team);
                        Log.i(TAG, "team:" + team);
                    }

                    mainThreadHandler.sendEmptyMessage(3);
                    Log.i(TAG, "onCreate++++++++++++++++ now what?");
                },
                response -> Log.i(TAG, "onCreate: failed to retrieve task" + response.toString())
        );

//       ============== shared preferences so info can be shared between the pages ===========

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String username = preferences.getString("username", "Guest");
        String userChosenTeam = preferences.getString("team", "Team: Undecided");

//     =======displaying the username ===========

        TextView usernameTextView = findViewById(R.id.usernameEditText);
        TextView usernameDisplayView = findViewById(R.id.usernameDisplayTextView);
        TextView userChosenTeamTextView = findViewById(R.id.userChosenTeam);
        usernameDisplayView.setText(username);
        userChosenTeamTextView.setText(userChosenTeam);


//     =======save button display and clicklistener ===========

        findViewById(R.id.saveButton).setOnClickListener(v -> {
            String user = usernameTextView.getText().toString();
            Team chosenTeam = (Team) spinner.getSelectedItem();
            String chosenTeamId = chosenTeam.getId();
            editor.putString("username", user);
            editor.putString("team", "Team: " + chosenTeam);
            editor.putString("teamId", chosenTeam.getId());
            Log.i(TAG, "settings page on click: chosen team id " + chosenTeamId);


            editor.apply();

            usernameDisplayView.setText(user);
            userChosenTeamTextView.setText("Team: " + chosenTeam.getName());
        });

    }
}