package com.example.signmeinfyp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayListView extends AppCompatActivity
{
    private String TAG = DisplayListView.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> moduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

        moduleList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetModDets().execute();
    }

    public class GetModDets extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DisplayListView.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

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
                try {
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

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
            else {
                Log.e(TAG, "Couldn't get json from server.");

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            SimpleAdapter adapter = new SimpleAdapter(DisplayListView.this, moduleList, R.layout.list_item, new String[]{ "moduleName","classListCourseCode"}, new int[]{R.id.modName, R.id.classList});
            lv.setAdapter(adapter);
        }
    }
}