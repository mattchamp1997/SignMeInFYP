package com.example.signmeinfyp;

import android.content.DialogInterface;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SignIn extends AppCompatActivity
{
    EditText code;
    Button signBtn;
    TextView welcome,or,tapTo,startTime,moduleName,classTypes,startHour,colon,startMins,classTypePlace,modNamePlace;
    ImageView nfc;

    JSONObject json;
    String classID,classType,moduleID,courseCode,room,lecID,modName,signInCode,startHr,startMin,finHr,finMin,lateHr,lateMin,dayy,monthh,yearr;

    AlertDialog.Builder builder;

    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        builder = new AlertDialog.Builder(this);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter!=null && nfcAdapter.isEnabled())
        {
            Toast.makeText(this,"NFC Available",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Enable NFC if you wish to tap for Sign-In",Toast.LENGTH_LONG).show();
        }

        welcome = findViewById(R.id.welcome);
        code = findViewById(R.id.code);
        or = findViewById(R.id.or);
        tapTo = findViewById(R.id.tap);
        nfc = findViewById(R.id.image);
        nfc.setImageResource(R.drawable.nfclogo);
        signBtn = findViewById(R.id.signBtn);

        modNamePlace = findViewById(R.id.modNamePlace);
        classTypePlace = findViewById(R.id.classTypePlace);

        startTime = findViewById(R.id.startTime);
        moduleName = findViewById(R.id.moduleName);
        classTypes = findViewById(R.id.classTypes);
        startHour = findViewById(R.id.startHour);
        colon = findViewById(R.id.colon);
        startMins = findViewById(R.id.startMins);

        //Catch data sent in INTENT from prior CLASS
        try {
            String json1 = getIntent().getStringExtra("ITEM_EXTRA");
            json = new JSONObject(json1);

            classID = json.getString("classID");
            classType  = json.getString("classType");
            moduleID = json.getString("moduleID");
            courseCode = json.getString("courseCode");
            room = json.getString("room");
            signInCode = json.getString("signInCode");
            lecID = json.getString("lecID");
            startHr = json.getString("startHr");
            startMin = json.getString("startMin");
            finHr = json.getString("finHr");
            finMin = json.getString("finMin");
            lateHr = json.getString("lateHr");
            lateMin = json.getString("lateMin");
            yearr = json.getString("year");
            monthh = json.getString("month");
            dayy = json.getString("day");
            modName = json.getString("moduleName");
            
            moduleName.setText(modName);
            classTypes.setText(classType);
            startHour.setText(startHr);
            startMins.setText(startMin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        signBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = new GregorianCalendar();
                int year       = calendar.get(Calendar.YEAR);
                int month      = calendar.get(Calendar.MONTH); // Jan = 0, not 1
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
                int minute     = calendar.get(Calendar.MINUTE);

                //1 for on time, 2 for late, default NULL for not attended at all
                String attended;

                //This allows students to sign in 3mins early for class
                int early = Integer.valueOf(startMin) - 3;

                if(year == Integer.valueOf(yearr))
                {
                    if(month+1 == Integer.valueOf(monthh))
                    {
                        if(dayOfMonth == Integer.valueOf(dayy))
                        {
                            //If ON TIME
                            if(hourOfDay == Integer.valueOf(startHr) && hourOfDay <= Integer.valueOf(lateHr))
                            {
                                if(minute >= early && minute <= Integer.valueOf(lateMin))
                                {
                                    if (code.getText().toString().equals(signInCode))
                                    {
                                        String studentID = LoginPage.getLoggedIn();
                                        String method = "classLogin";
                                        attended = "1";

                                        BackgroundTask backgroundTask = new BackgroundTask(SignIn.this);
                                        backgroundTask.execute(method,classID,studentID,attended);
                                    }
                                    else
                                    {
                                        ErrorPopupCode();
                                    }
                                }
                                else if(minute < Integer.valueOf(finMin) && minute > Integer.valueOf(lateMin))
                                {
                                    if (code.getText().toString().equals(signInCode))
                                    {
                                        String studentID = LoginPage.getLoggedIn();
                                        String method = "classLogin";
                                        attended = "2";

                                        BackgroundTask backgroundTask = new BackgroundTask(SignIn.this);
                                        backgroundTask.execute(method,classID,studentID,attended);
                                    }
                                    else
                                    {
                                        ErrorPopupCode();
                                    }
                                }
                                else
                                {
                                    ErrorPopup();
                                }
                            }
                            //If LATE
                            else if(hourOfDay <= Integer.valueOf(finHr) && hourOfDay >= Integer.valueOf(lateHr))
                            {
                                if(minute < Integer.valueOf(finMin) && minute > Integer.valueOf(lateMin))
                                {
                                    if (code.getText().toString().equals(signInCode))
                                    {
                                        String studentID = LoginPage.getLoggedIn();
                                        String method = "classLogin";
                                        attended = "2";

                                        BackgroundTask backgroundTask = new BackgroundTask(SignIn.this);
                                        backgroundTask.execute(method,classID,studentID,attended);
                                    }
                                    else
                                    {
                                        ErrorPopupCode();
                                    }
                                }
                                else
                                {
                                    ErrorPopup();
                                }
                            }
                            else
                            {
                                ErrorPopup();
                            }
                        }
                        else
                        {
                            ErrorPopupDate();
                        }
                    }
                    else
                    {
                        ErrorPopupDate();
                    }
                }
                else
                {
                    ErrorPopupDate();
                }
            }
        });
    }

    public void ErrorPopup()
    {
        builder.setMessage("You are unable to sign in for this class. You are either too early or too late.");

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

    public void ErrorPopupDate()
    {
        builder.setMessage("The class you are trying to sign in for is not today.");

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

    public void ErrorPopupCode()
    {
        builder.setMessage("The Sign-In code you entered is incorrect.");

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
}
