package com.example.signmeinfyp.ui.MyModules;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.signmeinfyp.DisplayListView;
import com.example.signmeinfyp.NewModule;
import com.example.signmeinfyp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyModulesFragment extends Fragment
{
    FloatingActionButton fabmod;
    TextView textViewJson;
    Button getJson,parseJSON;
    String JSON_STRING;
    String json_string;

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
                getJSON(v);
            }
        });

        parseJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSON(v);
            }
        });

        return view;
    }

    public void getJSON(View view)
    {
        new BackgroundTaskJSON().execute();
    }

    public void parseJSON(View view)
    {
        if(json_string==null)
        {
            Toast.makeText(getActivity(),"First Get JSON",Toast.LENGTH_LONG);
        }
        else
        {
            Intent intent = new Intent(getActivity(), DisplayListView.class);
            intent.putExtra("json_date",json_string);
            startActivity(intent);
        }
    }

    class BackgroundTaskJSON extends AsyncTask<Void, Void, String>
    {
        String json_url;

        @Override
        protected void onPreExecute()
        {
            json_url = "http://mattfyp.000webhostapp.com/json_getdata.php";
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            //Line to remove HTML tags from JSON
            String nohtml = android.text.Html.fromHtml(result).toString();
            textViewJson.setText(nohtml);
            json_string = nohtml;
        }
    }
}