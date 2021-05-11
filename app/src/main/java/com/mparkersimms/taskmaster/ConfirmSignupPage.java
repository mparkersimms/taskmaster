package com.mparkersimms.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class ConfirmSignupPage extends AppCompatActivity {
String TAG = "msimms_confirmSignupPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_signup_page);
        String username = getIntent().getStringExtra("username");
        ((TextView)findViewById(R.id.textViewConfirmUsername)).setText(username);

        findViewById(R.id.confirmSignupButton).setOnClickListener(v -> {
            String confirmationCode = ((TextView)(findViewById(R.id.editTextConfirmationCode))).getText().toString();

            Amplify.Auth.confirmSignUp(
                    username,
                    confirmationCode,
                    r-> {
                        Intent intent = new Intent(ConfirmSignupPage.this, MainActivity.class);
                        startActivity(intent);
                    },
                    r -> {
                        Log.i(TAG, "confirmationpage: unsuccessfull");
                    }
            );
        });
    }
}