package com.example.signmeinfyp.ui.NewModules;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.signmeinfyp.BackgroundTask;
import com.example.signmeinfyp.R;

public class NewModFragment extends Fragment
{
    TextView welcome;
    EditText modname,lecId,courseCode;
    Button create;

    AlertDialog.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_newmod, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        modname = view.findViewById(R.id.modName);
        lecId = view.findViewById(R.id.lecID);
        courseCode = view.findViewById(R.id.courseCode);
        create = view.findViewById(R.id.createBtn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modname.getText().toString().equals("") || lecId.getText().toString().equals("") || courseCode.getText().toString().equals(""))
                {
                    builder = new AlertDialog.Builder(getActivity());
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

                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(method,modName,lecID,courseCyode);
                }
            }
        });

    }
}
