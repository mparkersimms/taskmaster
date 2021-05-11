package com.mparkersimms.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignupPage extends AppCompatActivity {
String TAG = "msimms_signupPageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        findViewById(R.id.signupSubmitButton).setOnClickListener(v -> {
        String username = ((TextView)findViewById(R.id.editTextSignupEmailAddress)).getText().toString();
        String password = ((TextView)findViewById(R.id.editTextSignupPassword)).getText().toString();

            Amplify.Auth.signUp(
                    username,
                    password,
                    AuthSignUpOptions.builder().build(),
                    r -> {
                        Log.i(TAG, "signupCognito: successfull" + r.toString());
                        Intent intent = new Intent(SignupPage.this, ConfirmSignupPage.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    },
                    r -> {
                        Log.i(TAG, "signupCognito: unsuccessfull " + r.toString());
                    }
            );


        });


    }
}