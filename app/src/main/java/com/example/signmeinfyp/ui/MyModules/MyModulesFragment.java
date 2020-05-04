package com.example.signmeinfyp.ui.MyModules;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.signmeinfyp.HttpHandler;
import com.example.signmeinfyp.LoginPage;
import com.example.signmeinfyp.R;
import com.example.signmeinfyp.ui.MyModules.NewModule.NewModule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyModulesFragment extends Fragment
{
    private String TAG = MyModulesFragment.class.getSimpleName();
    private ListView lv;
    FloatingActionButton fab;
    String loggedIn = LoginPage.getLoggedIn();

    ArrayList<HashMap<String, String>> moduleList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_mymodules, container, false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        //can add listener elements here
        moduleList = new ArrayList<>();
        lv = (ListView)view.findViewById(R.id.list);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                JSONObject module = new JSONObject(moduleList.get(position));

                Intent intent = new Intent(getActivity(), ModuleView.class);
                intent.putExtra("ITEM_EXTRA", module.toString());
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to create new module
                Intent intent = new Intent(getActivity(), NewModule.class);
                getActivity().startActivity(intent);
            }
        });

        new GetModDets().execute();
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    private class GetModDets extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = "https://mattfyp.000webhostapp.com/json_getdata.php";

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null)
            {
                try
                {
                    //Code for downloading JSON reply
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray modDetails = jsonObj.getJSONArray("server_response");

                    // looping through All Modules
                    for (int i = 0; i < modDetails.length(); i++) {
                        JSONObject c = modDetails.getJSONObject(i);
                        String modID = c.getString("moduleID");
                        String lecID = c.getString("lecturerID");
                        String modName = c.getString("moduleName");
                        String classList = c.getString("classListCourseCode");

                        // tmp hash map for single module
                        HashMap<String, String> modDets = new HashMap<>();

                        // adding each child node to HashMap key => value
                        modDets.put("moduleID", modID);
                        modDets.put("lecturerID", lecID);
                        modDets.put("moduleName", modName);
                        modDets.put("classListCourseCode", classList);

                        // adding module to module list
                        moduleList.add(modDets);
                    }
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");

                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"Couldn't get json from server.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), moduleList, R.layout.list_item, new String[]{ "moduleName","classListCourseCode"}, new int[]{R.id.classType, R.id.roomNum});
            lv.setAdapter(adapter);
        }
    }
}