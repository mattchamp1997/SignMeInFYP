package com.example.signmeinfyp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText fullName, email, RegIdNumber, RegPassword, RegPasswordVerify,courseCode;
    RadioGroup radioGroup;
    RadioButton radioButton, radioLec,radioStu;
    Button register;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        radioGroup = findViewById(R.id.radioGroup);
        radioLec = findViewById(R.id.radioLecturer);
        radioStu = findViewById(R.id.radioStudent);
        RegIdNumber = findViewById(R.id.RegIdNumber);
        RegPassword = findViewById(R.id.RegPassword);
        RegPasswordVerify = findViewById(R.id.RegPasswordVerify);
        courseCode = findViewById(R.id.courseCode);

        courseCode.setVisibility(View.INVISIBLE);


        register = findViewById(R.id.registerConfirm);

        //When register button clicked
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //If fields fullname, email, IDNum OR password are empty, dialog box will popup asking the user to fill all fields
                if(fullName.getText().toString().equals("") || email.getText().toString().equals("") || RegIdNumber.getText().toString().equals("") || RegPassword.getText().toString().equals(""))
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

                //If registering as student and course code not entered
                else if(radioStu.isChecked() && courseCode.getText().toString().equals(""))
                {
                    builder = new AlertDialog.Builder(Register.this);
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("If registering as a student, please specify your course code. For example, DT211//4");

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

                //If user type is not selected
                else if(! radioStu.isChecked() & ! radioLec.isChecked())
                {
                    builder = new AlertDialog.Builder(Register.this);
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please select an Account Type");

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

                //If passwords do not match, create popup to notify user
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

                else
                {
                    //Code to pass user's selected account type to db
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    String accountType = radioButton.getText().toString();

                    String name = fullName.getText().toString();
                    String regemail = email.getText().toString();
                    String id = RegIdNumber.getText().toString();
                    String pass = RegPassword.getText().toString();
                    String method = "register";

                    BackgroundTask backgroundTask = new BackgroundTask(Register.this);
                    backgroundTask.execute(method,name,regemail,id,pass,accountType);

                    //finish();
                }
            }
        });

        //To show EditText for course code if registering as student
        radioStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (radioStu.isChecked())
                {
                    courseCode.setVisibility(View.VISIBLE);
                }
            }
        });

        //Hide EditText for course code if Lecturer
        radioLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (radioLec.isChecked())
                {
                    courseCode.setVisibility(View.INVISIBLE);
                }
            }
        });




    }

    //Called in Register Layout XML
    public void checkButton(View v)
    {
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        Toast.makeText(this, "\"selected radio button: \"" + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
