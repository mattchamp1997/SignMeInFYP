package com.example.signmeinfyp.ui.MyModules;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signmeinfyp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassView extends AppCompatActivity
{
    String lecturerID,CLcourseCode,modName,modID;
    JSONObject json;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_view);

        //Catch data sent in INTENT from prior CLASS
        try {
            String json1 = getIntent().getStringExtra("ITEM_EXTRA");
            json = new JSONObject(json1);

            lecturerID = json.getString("lecturerID");
            CLcourseCode = json.getString("classListCourseCode");
            modName = json.getString("moduleName");
            modID = json.getString("moduleID");

            //ModuleView.setIDModule();

            //tv1.setText(modName);
            //tv2.setText(lecturerID);
            //tv3.setText(CLcourseCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
