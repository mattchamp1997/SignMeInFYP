package com.example.signmeinfyp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity
{
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ModListViewAdapter modListViewAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

        listView = (ListView)findViewById(R.id.listview);
        modListViewAdapter = new ModListViewAdapter(this, R.layout.row_layout);
        listView.setAdapter(modListViewAdapter);

        json_string = getIntent().getExtras().getString("json_data");

        try
        {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;

            int moduleID;
            String lecID, modName, classList;

            while(count < jsonObject.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                moduleID = JO.getInt("moduleID");
                lecID = JO.getString("lecturerID");
                modName = JO.getString("moduleName");
                classList = JO.getString("classListCourseCode");

                ModListViewDetails modListViewDetails = new ModListViewDetails(moduleID,lecID,modName,classList);

                modListViewAdapter.add(modListViewDetails);

                count++;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
