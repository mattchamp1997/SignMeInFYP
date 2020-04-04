package com.example.signmeinfyp.ui.MyModules;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signmeinfyp.HttpHandler;
import com.example.signmeinfyp.LoginPage;
import com.example.signmeinfyp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ModuleView extends AppCompatActivity
{
    private String TAG = ModuleView.class.getSimpleName();
    private ListView lv1;
    FloatingActionButton fab1;
    String loggedIn = LoginPage.getLoggedIn();

    ArrayList<HashMap<String, String>> classList;

    JSONObject json;
    TextView tv1,tv2,tv3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_view);

        classList = new ArrayList<>();
        lv1 = (ListView) findViewById(R.id.list1);
        fab1 = findViewById(R.id.fab1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        //Catch data sent in INTENT from prior CLASS
        try {
            json = new JSONObject(getIntent().getStringExtra("ITEM_EXTRA"));
            Log.e(TAG, "Example Item: " + json.getString("KEY"));
            String modID = json.getString("moduleID");
            String lecID = json.getString("lecturerID");
            String courseCode = json.getString("classListCourseCode");
            String modName = json.getString("moduleName");

            tv1.setText(modName);
            tv2.setText(lecID);
            tv3.setText(courseCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject modDetsJSON = new JSONObject();
                //Pass mod details JSON to create class
                try {
                    modDetsJSON.put("modID", json.getString("moduleID"));
                    modDetsJSON.put("lecID", json.getString("lecID"));
                    modDetsJSON.put("courseCode", json.getString("courseCode"));
                    modDetsJSON.put("modName", json.getString("modName"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, NewClass.class);
                //intent.putExtra("ITEM_EXTRA", modDetsJSON.toString());
                startActivity(intent);
            }
        });

        new GetClassDets().execute();
    }

    public class GetClassDets extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(ModuleView.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpHandler sh1 = new HttpHandler();

            // Making a request to url and getting response
            String url = "https://mattfyp.000webhostapp.com/json_getdata.php";

            String jsonStr = sh1.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray classDetailsArr = jsonObj.getJSONArray("server_response");

                    // looping through All Modules
                    for (int i = 0; i < classDetailsArr.length(); i++) {
                        JSONObject c = classDetailsArr.getJSONObject(i);
                        String modID = c.getString("moduleID");
                        String lecID = c.getString("lecturerID");
                        String modName = c.getString("moduleName");
                        String courseCode = c.getString("classListCourseCode");

                        // tmp hash map for single module
                        HashMap<String, String> classDets = new HashMap<>();

                        // adding each child node to HashMap key => value
                        classDets.put("moduleID", modID);
                        classDets.put("lecturerID", lecID);
                        classDets.put("moduleName", modName);
                        classDets.put("classListCourseCode", courseCode);

                        // adding module to module list
                        classList.add(classDets);
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
            SimpleAdapter adapter = new SimpleAdapter(ModuleView.this, classList, R.layout.list_item, new String[]{ "moduleName","classListCourseCode"}, new int[]{R.id.modName, R.id.classList});
            lv1.setAdapter(adapter);
        }
    }
}
