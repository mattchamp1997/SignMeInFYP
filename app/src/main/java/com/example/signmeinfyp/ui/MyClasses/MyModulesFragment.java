package com.example.signmeinfyp.ui.MyClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.signmeinfyp.NewModule;
import com.example.signmeinfyp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyModulesFragment extends Fragment
{
    FloatingActionButton fabmod;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_modules, container, false);

        fabmod = view.findViewById(R.id.mymoduleFAB);

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

        return view;
    }
}