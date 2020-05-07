package com.example.signmeinfyp.ui.Calendar;

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
import com.example.signmeinfyp.SignIn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CalendarFragment extends Fragment {

    private String TAG = CalendarFragment.class.getSimpleName();
    private ListView lv;
    FloatingActionButton fab;
    String loggedIn = LoginPage.getLoggedIn();

    ArrayList<HashMap<String, String>> calendarList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_mymodules, container, false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        //can add listener elements here
        calendarList = new ArrayList<>();
        lv = (ListView)view.findViewById(R.id.list);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        if(LoginPage.getUserType().equals("Student"))
        {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    JSONObject classInfo = new JSONObject(calendarList.get(position));

                    Intent intent = new Intent(getActivity(), SignIn.class);
                    intent.putExtra("ITEM_EXTRA", classInfo.toString());
                    startActivity(intent);
                }
            });
        }


        /*if(LoginPage.getUserType().equals("Lecturer"))
        {
            fab.setVisibility(View.VISIBLE);
        }*/

        new CalendarFragment.CalendarDets().execute();
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    private class CalendarDets extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpHandler sh = new HttpHandler();

            String url = "https://mattfyp.000webhostapp.com/calendar.php";

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null)
            {
                try
                {
                    //Code for downloading JSON reply
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray calDetails = jsonObj.getJSONArray("server_response");

                    // looping through All Modules
                    for (int i = 0; i < calDetails.length(); i++) {
                        JSONObject c = calDetails.getJSONObject(i);
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
                        String modName = c.getString("moduleName");

                        // temp hash map for single module
                        HashMap<String, String> calDets = new HashMap<>();

                        // adding each child node to HashMap key => value
                        calDets.put("classID", classID);
                        calDets.put("classType", classType);
                        calDets.put("moduleID", moduleID);
                        calDets.put("courseCode", courseCode);
                        calDets.put("room", room);
                        calDets.put("signInCode", signInCode);
                        calDets.put("lecID", lecID);
                        calDets.put("startHr", startHr);
                        calDets.put("startMin", startMin);
                        calDets.put("finHr", finHr);
                        calDets.put("finMin", finMin);
                        calDets.put("lateHr", lateHr);
                        calDets.put("lateMin", lateMin);
                        calDets.put("year", year);
                        calDets.put("month", month);
                        calDets.put("day", day);
                        calDets.put("moduleName", modName);

                        // adding module to module list
                        calendarList.add(calDets);
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
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), calendarList, R.layout.list_item4, new String[]{"moduleName","classType","room","startHr","startMin","finHr","finMin","day","month","year"}, new int[]{R.id.modName, R.id.classTypes, R.id.roomNum, R.id.startHr, R.id.startMin, R.id.finishHr,R.id.finishMin, R.id.day,R.id.month,R.id.year});
            lv.setAdapter(adapter);
        }
    }
}