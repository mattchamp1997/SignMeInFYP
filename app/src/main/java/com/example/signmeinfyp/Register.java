package com.example.signmeinfyp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText fullName, email, RegIdNumber, RegPassword, RegPasswordVerify;
    Button register;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        RegIdNumber = findViewById(R.id.RegIdNumber);
        RegPassword = findViewById(R.id.RegPassword);
        RegPasswordVerify = findViewById(R.id.RegPasswordVerify);
        register = findViewById(R.id.registerConfirm);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullName.getText().toString().equals("") || email.getText().toString().equals("") || RegPassword.getText().toString().equals(""))
                {
                    builder = new AlertDialog.Builder(Register.this);
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please fill all fields");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                else if (! RegPassword.getText().toString().equals(RegPasswordVerify.getText().toString()))
                {
                    builder = new AlertDialog.Builder(Register.this);
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please ensure passwords match");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            RegPassword.setText("");
                            RegPasswordVerify.setText("");
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });
    }

    private void returnToLogin()
    {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);

    }
}
