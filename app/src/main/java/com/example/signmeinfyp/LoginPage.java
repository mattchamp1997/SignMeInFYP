package com.example.signmeinfyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity
{
    EditText LoginIdNumber, LoginPassword;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginIdNumber = findViewById(R.id.LoginIdNumber);
        LoginPassword = findViewById(R.id.LoginPassword);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.register);

        LoginIdNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, Register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerIntent();
            }
        });
    }

    private void registerIntent()
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void loginIntent()
    {
        Intent intent = new Intent(this, MyClasses.class);
        startActivity(intent);
    }

}
