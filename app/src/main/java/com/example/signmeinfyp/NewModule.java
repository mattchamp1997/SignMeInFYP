package com.example.signmeinfyp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewModule extends AppCompatActivity
{
    EditText modname,lecId,courseCode;
    Button create;

    AlertDialog.Builder builder;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmod);

        modname = findViewById(R.id.modName);
        lecId = findViewById(R.id.lecID);
        courseCode = findViewById(R.id.courseCode);
        create = findViewById(R.id.createBtn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modname.getText().toString().equals("") || lecId.getText().toString().equals("") || courseCode.getText().toString().equals(""))
                {
                    builder = new AlertDialog.Builder(NewModule.this);
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

                else
                {
                    String modName = modname.getText().toString();
                    String lecID = lecId.getText().toString();
                    String courseCyode = courseCode.getText().toString();
                    String method = "newModule";

                    BackgroundTask backgroundTask = new BackgroundTask(NewModule.this);
                    backgroundTask.execute(method,modName,lecID,courseCyode);
                }
            }
        });

    }
}
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
