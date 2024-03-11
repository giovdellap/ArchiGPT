package com.example.marketinganalyzer.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

public class WelcomeActivity extends DefaultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get the intent and its data.
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = findViewById(R.id.order_textview);
        //textView.setText(message);

        Button buttonLogin = findViewById(R.id.welcome_button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,
                        LoginActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
                finish();
            }
        });

        Button buttonSignup = findViewById(R.id.welcome_button_signup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,
                        SignupActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void layoutOnAPIReturn(JSONObject response) {

    }

    @Override
    protected void layoutBeforeAPICall() {

    }

    @Override
    protected void invokeApi() throws Exception {

    }
}
