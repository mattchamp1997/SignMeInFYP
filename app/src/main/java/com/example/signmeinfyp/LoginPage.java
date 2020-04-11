package com.example.signmeinfyp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity
{
    EditText LoginIdNumber, LoginPassword;
    Button loginButton, registerButton;
    private static String loggedIn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        builder = new AlertDialog.Builder(this);

        LoginIdNumber = findViewById(R.id.LoginIdNumber);
        LoginPassword = findViewById(R.id.LoginPassword);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginPage.this, Register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginIdNumber.getText().toString().equals("") || LoginPassword.getText().toString().equals(""))
                {
                    builder.setMessage("Please fill in all fields.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle("Error");
                    alertDialog.show();
                }
                else
                {
                    String idNum = LoginIdNumber.getText().toString();
                    String pass = LoginPassword.getText().toString();
                    String method = "login";
                    setLoggedIn(idNum);

                    BackgroundTask backgroundTask = new BackgroundTask(LoginPage.this);
                    backgroundTask.execute(method,idNum,pass);
                }
            }
        });
    }

    public static String getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }
}
