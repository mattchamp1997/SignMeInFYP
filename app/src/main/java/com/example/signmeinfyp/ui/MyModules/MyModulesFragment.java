package com.example.signmeinfyp.ui.MyModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.signmeinfyp.DisplayListView;
import com.example.signmeinfyp.NewModule;
import com.example.signmeinfyp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyModulesFragment extends Fragment
{
    FloatingActionButton fabmod;
    TextView textViewJson;
    Button getJson,parseJSON;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_modules, container, false);

        fabmod = view.findViewById(R.id.mymoduleFAB);
        textViewJson = view.findViewById(R.id.textView);
        getJson = view.findViewById(R.id.getjson);
        parseJSON = view.findViewById(R.id.parsejson);

        fabmod.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(this, "\"selected radio button: \"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewModule.class);
                startActivity(intent);
            }
        });

        getJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayListView.class);
                startActivity(intent);
            }
        });

        return view;
    }
}