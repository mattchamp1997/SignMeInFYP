package com.example.signmeinfyp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewModule extends AppCompatActivity
{
    TextView welcome;
    EditText modname,lecId;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmod);

        welcome = findViewById(R.id.WelcometoModule);
        modname = findViewById(R.id.modName);
        lecId = findViewById(R.id.lecID);

        /*createModButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String LecIdNum = lecId.getText().toString();
                String modName = modname.getText().toString();
                String method = "login";

                BackgroundTask backgroundTask = new BackgroundTask(NewModule.this);
                backgroundTask.execute(method,LecIdNum,modName);
            }
        });*/

    }
}
