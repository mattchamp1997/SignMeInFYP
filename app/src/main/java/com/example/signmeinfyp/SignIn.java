package com.example.signmeinfyp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity
{
    EditText code;
    TextView welcome, or, tapTo;
    ImageView nfc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        welcome = findViewById(R.id.welcome);
        code = findViewById(R.id.code);
        or = findViewById(R.id.or);
        tapTo = findViewById(R.id.tap);
        nfc = findViewById(R.id.image);
        nfc.setImageResource(R.drawable.nfclogo);
    }

}
