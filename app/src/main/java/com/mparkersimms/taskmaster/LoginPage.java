package com.mparkersimms.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class LoginPage extends AppCompatActivity {
    String TAG = "msimms_loginpPageActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        findViewById(R.id.loginSubmitButton).setOnClickListener(v -> {
            String username = ((TextView)findViewById(R.id.editTextLoginEmailAddress)).getText().toString();
            String password = ((TextView)findViewById(R.id.editTextLoginPassword)).getText().toString();

            Amplify.Auth.signIn(
                    username,
                    password,
                    r -> {
                        Log.i(TAG, "LoginCognito: successfull" + r.toString());
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        startActivity(intent);
                    },
                    r -> {
                        Log.i(TAG, "LoginCognito: unsuccessfull " + r.toString());
                    }
            );


        });
    }
}