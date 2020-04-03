package com.example.signmeinfyp.ui.MyModules.NewClass;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.signmeinfyp.BackgroundTask;
import com.example.signmeinfyp.R;

import java.util.Calendar;

public class NewClass extends AppCompatActivity
{
    EditText room, loginCode;
    TextView classType,dateTV,timeTV,timeTV2,timeTV3;
    RadioGroup radioGroup;
    RadioButton radioButton, rbLec,rbTutorial,rbLab;
    Button dateButton,timeButton, timeButton2, timeButton3, createClass;
    AlertDialog.Builder builder;

    int startHr, startMin, finHr, finMin, lateHr, lateMin;
    int year, month, day;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclass);

        room = findViewById(R.id.room);
        loginCode = findViewById(R.id.loginCode);
        classType = findViewById(R.id.classType);
        dateTV = findViewById(R.id.dateTV);
        timeTV = findViewById(R.id.timeTV);
        timeTV2 = findViewById(R.id.timeTV2);
        timeTV3 = findViewById(R.id.timeTV3);
        radioGroup = findViewById(R.id.radioGroup2);
        rbLab = findViewById(R.id.rbLab);
        rbLec = findViewById(R.id.rbLec);
        rbTutorial = findViewById(R.id.rbTutorial);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        timeButton2 = findViewById(R.id.timeButton2);
        timeButton3 = findViewById(R.id.timeButton3);
        createClass = findViewById(R.id.createClass);

        builder = new AlertDialog.Builder(this);

        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room.getText().toString().equals("") || loginCode.getText().toString().equals(""))
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
                else if(timeTV.getText().toString().equals("") || timeTV2.getText().toString().equals("") || timeTV3.getText().toString().equals(""))
                {
                    builder.setMessage("Please select a start time, finish time and late time.");

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
                else if(dateTV.getText().toString().equals(""))
                {
                    builder.setMessage("Please select a date.");

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
                else if(! rbLab.isChecked() & ! rbLec.isChecked() & !rbTutorial.isChecked())
                {
                    builder.setMessage("Please select class type.");

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
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    String classType = radioButton.getText().toString();

                    String method = "newClass";
                    int modID;
                    String modName;
                    String lecID;
                    String room;
                    //string classtype; ^^
                    int day,month,year;
                    int starthour,startmin,finhour,finmin,latehour,latemin;

                    //String name = fullName.getText().toString();
                    //String regemail = email.getText().toString();

                    BackgroundTask backgroundTask = new BackgroundTask(NewClass.this);
                    //backgroundTask.execute(method,name,regemail,id,pass,accountType,courseCode);
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewClass.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateTV.setText("Date: " + day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }

        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewClass.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute < 10 && selectedMinute > 0)
                        {
                            timeTV.setText("Start Time: " + selectedHour + ":0" + selectedMinute);
                        }
                        else if (selectedMinute == 0)
                        {
                            timeTV.setText("Start Time: " + selectedHour + ":00");
                        }
                        else
                        {
                            timeTV.setText("Start Time: " + selectedHour + ":" + selectedMinute);
                        }
                        startHr = selectedHour;
                        startMin = selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        timeButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewClass.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        if(selectedHour > startHr)
                        {
                            if (selectedMinute < 10 && selectedMinute > 0) {
                                timeTV2.setText("Finish Time: " + selectedHour + ":0" + selectedMinute);
                            } else if (selectedMinute == 0) {
                                timeTV2.setText("Finish Time: " + selectedHour + ":00");
                            } else {
                                timeTV2.setText("Finish Time: " + selectedHour + ":" + selectedMinute);
                            }
                            finHr = selectedHour;
                            finMin = selectedMinute;
                        }
                        else if(selectedHour == startHr)
                        {
                            if(selectedMinute > startMin)
                            {
                                if (selectedMinute < 10 && selectedMinute > 0) {
                                    timeTV2.setText("Finish Time: " + selectedHour + ":0" + selectedMinute);
                                } else if (selectedMinute == 0) {
                                    timeTV2.setText("Finish Time: " + selectedHour + ":00");
                                } else {
                                    timeTV2.setText("Finish Time: " + selectedHour + ":" + selectedMinute);
                                }
                                finHr = selectedHour;
                                finMin = selectedMinute;
                            }
                            else
                            {
                                builder.setMessage("Please select a finish time later than the start time");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setTitle("Error");
                                alertDialog.show();
                            }
                        }
                        else
                        {
                            builder.setMessage("Please select a finish time later than the start time");

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
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        timeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewClass.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        if(selectedHour > finHr || selectedHour < startHr)
                        {
                            builder.setMessage("Please select a late time during the class");
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
                        else if (selectedHour == startHr && selectedHour == finHr)
                        {
                            if(selectedMinute < startMin || selectedMinute > finMin)
                            {
                                builder.setMessage("Please select a late time during the class");
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
                            else if(selectedMinute > startMin && selectedMinute < finMin)
                            {
                                if (selectedMinute < 10 && selectedMinute > 0) {
                                    timeTV3.setText("Late Time: " + selectedHour + ":0" + selectedMinute);
                                } else if (selectedMinute == 0) {
                                    timeTV3.setText("Late Time: " + selectedHour + ":00");
                                } else {
                                    timeTV3.setText("Late Time: " + selectedHour + ":" + selectedMinute);
                                }
                                lateHr = selectedHour;
                                lateMin = selectedMinute;
                            }
                        }
                        else {
                            builder.setMessage("Please select a late time during the class");
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
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }


}
