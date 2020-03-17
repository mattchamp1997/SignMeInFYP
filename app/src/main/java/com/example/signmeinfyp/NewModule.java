package com.example.signmeinfyp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewModule extends AppCompatActivity
{
    TextView welcome,info;
    EditText modname,lecId,studentCount;
    Button confirm;
    //EditText textIn;
    //Button buttonAdd;
    //LinearLayout container;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmod);

        welcome = findViewById(R.id.WelcometoModule);
        modname = findViewById(R.id.modName);
        lecId = findViewById(R.id.lecID);
        info = findViewById(R.id.info);
        studentCount = findViewById(R.id.countStudent);
        confirm = findViewById(R.id.confirmBtn);


        /*textIn = (EditText)findViewById(R.id.textin);
        buttonAdd = (Button)findViewById(R.id.add);
        container = (LinearLayout)findViewById(R.id.container);

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(textIn.getText().toString());
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);

                buttonRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }
                });

                container.addView(addView);
            }
        });*/

    }
}
        //

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
