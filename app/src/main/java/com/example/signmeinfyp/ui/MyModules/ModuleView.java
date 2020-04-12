package com.example.signmeinfyp.ui.MyModules;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signmeinfyp.HttpHandler2;
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
    private static String method;
    private static String IDmodule;

    private String TAG = ModuleView.class.getSimpleName();
    private ListView lv1;
    FloatingActionButton fab1;
    String loggedIn = LoginPage.getLoggedIn();
    String hash = "Room: ";

    private static String IDModule;

    //Strings for INTENT JSON
    String modID,lecturerID,CLcourseCode,modName;
    
    //Strings for LISTVIEW
    //String classID,classType,moduleID,courseCode,room,signInCode,lecID,startHr,startMin,finHr,finMin,lateHr,lateMin,year,month,day;

    ArrayList<HashMap<String, String>> classList;

    JSONObject json;
    TextView tv1,tv2,tv3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_view);

        fab1 = findViewById(R.id.fab1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        //Catch data sent in INTENT from prior CLASS
        try {
            String json1 = getIntent().getStringExtra("ITEM_EXTRA");
            json = new JSONObject(json1);
            //Log.e(TAG, "Example Item: " + json.getString("KEY"));

            lecturerID = json.getString("lecturerID");
            CLcourseCode = json.getString("classListCourseCode");
            modName = json.getString("moduleName");
            modID = json.getString("moduleID");

            setIDModule(modID);

            tv1.setText(modName);
            tv2.setText(lecturerID);
            tv3.setText(CLcourseCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        classList = new ArrayList<>();
        lv1 = (ListView) findViewById(R.id.list1);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ModuleView.this, json.toString(), Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(ModuleView.this, ClassView.class);
                //intent.putExtra("ITEM_EXTRA", json.toString());
                //startActivity(intent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleView.this, NewClass.class);
                intent.putExtra("ITEM_EXTRA", json.toString());
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
            HttpHandler2 sh1 = new HttpHandler2();

            // Making a request to url and getting response
            String url = "http://mattfyp.000webhostapp.com/modviewclasses.php";

            String jsonStr = sh1.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray classDetailsArr = jsonObj.getJSONArray("server_response");

                    // looping through All classes
                    for (int i = 0; i < classDetailsArr.length(); i++) {
                        JSONObject c = classDetailsArr.getJSONObject(i);
                        String classID = c.getString("classID");
                        String classType  = c.getString("classType");
                        String moduleID = c.getString("moduleID");
                        String courseCode = c.getString("courseCode");
                        String room = c.getString("room");
                        String signInCode = c.getString("signInCode");
                        String lecID = c.getString("lecID");
                        String startHr = c.getString("startHr");
                        String startMin = c.getString("startMin");
                        String finHr = c.getString("finHr");
                        String finMin = c.getString("finMin");
                        String lateHr = c.getString("lateHr");
                        String lateMin = c.getString("lateMin");
                        String year = c.getString("year");
                        String month = c.getString("month");
                        String day = c.getString("day");

                        // temp hash map for single module
                        HashMap<String, String> classDets = new HashMap<>();

                        // adding each child node to HashMap key => value
                        classDets.put("classID", classID);
                        classDets.put("classType", classType);
                        classDets.put("moduleID", moduleID);
                        classDets.put("courseCode", courseCode);
                        classDets.put("room", room);
                        classDets.put("signInCode", signInCode);
                        classDets.put("lecID", lecID);
                        classDets.put("startHr", startHr);
                        classDets.put("startMin", startMin);
                        classDets.put("finHr", finHr);
                        classDets.put("finMin", finMin);
                        classDets.put("lateHr", lateHr);
                        classDets.put("lateMin", lateMin);
                        classDets.put("year", year);
                        classDets.put("month", month);
                        classDets.put("day", day);

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
            SimpleAdapter adapter = new SimpleAdapter(ModuleView.this, classList, R.layout.list_item3, new String[]{"classType","room","startHr","startMin","finHr","finMin","day","month","year"}, new int[]{R.id.classType, R.id.roomNum, R.id.startHr, R.id.startMin, R.id.finishHr,R.id.finishMin, R.id.day,R.id.month,R.id.year});
            lv1.setAdapter(adapter);
        }
    }

    public static String getIDModule() {
        return IDModule;
    }

    public static void setIDModule(String IDModule) {
        ModuleView.IDModule = IDModule;
    }

}
