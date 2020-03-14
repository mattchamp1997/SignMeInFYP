package com.example.signmeinfyp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String,Void,String>
{
    String register_url = "http://mattfyp.000webhostapp.com/register.php";
    String login_url = "http://mattfyp.000webhostapp.com/login.php";

    Context ctx;
    Activity activity;

    AlertDialog.Builder builder;
    //ProgressDialog progressDialog;

    public BackgroundTask(Context ctx)
    {
        this.ctx = ctx;
        activity = (Activity)ctx;
    }


    @Override
    protected void onPreExecute()
    {
        builder = new AlertDialog.Builder(ctx);

        /*
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Connecting to server");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        */
    }

    @Override
    protected String doInBackground(String... params)
    {
        String method = params[0];

        //For Register
        if (method.equals("register"))
        {
            String fullName = params[1];
            String email = params[2];
            String idNum = params[3];
            String password = params[4];
            String accountType = params[5];

            try
            {
                URL url = new URL(register_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String data = URLEncoder.encode("fullName", "utf-8") + "=" + URLEncoder.encode(fullName,"utf-8") + "&" +
                        URLEncoder.encode("email", "utf-8") + "=" + URLEncoder.encode(email,"utf-8") + "&" +
                        URLEncoder.encode("idNum", "utf-8") + "=" + URLEncoder.encode(idNum,"utf-8") + "&" +
                        URLEncoder.encode("password", "utf-8") + "=" + URLEncoder.encode(password,"utf-8") + "&" +
                        URLEncoder.encode("accountType", "utf-8") + "=" + URLEncoder.encode(accountType,"utf-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                //Get server response - successful insert or not
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line=bufferedReader.readLine()) !=null)
                {
                    stringBuilder.append(line+"\n");
                }

                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

                //inputStream.close();
                //return "Registration Success";
                //httpURLConnection.disconnect();
                //Thread.sleep(5000);
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //For Login
        else if (method.equals("login"))
        {
            try
            {
                URL url = new URL(login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                String idNum,password;
                idNum = params[1];
                password = params[2];

                String data = URLEncoder.encode("idNum", "utf-8") + "=" + URLEncoder.encode(idNum,"utf-8") + "&" +
                        URLEncoder.encode("password", "utf-8") + "=" + URLEncoder.encode(password,"utf-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                //Get server response - successful insert or not
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line=bufferedReader.readLine()) !=null)
                {
                    stringBuilder.append(line+"\n");
                }

                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String json)
    {
        try
        {
            //progressDialog.dismiss();
            //Line to remove HTML tags from JSON
            String nohtml = android.text.Html.fromHtml(json).toString();

            Toast.makeText(ctx,nohtml,Toast.LENGTH_LONG).show();

            JSONObject jsonObject = new JSONObject(nohtml);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("message");

            if(code.equals("reg_true"))
            {
                showDialog("Registration Successful", message,code);

            }
            else if(code.equals("reg_false"))
            {
                showDialog("Registration Failed", message,code);
            }
            else if(code.equals("login_true"))
            {
                Intent intent = new Intent(activity, Main.class);
                activity.startActivity(intent);
            }
            else if(code.equals("login_false"))
            {
                showDialog("Login Failed", message,code);
            }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void showDialog (String title, String message, String code)
    {
        builder.setTitle(title);

        if(code.equals("reg_true"))
        {
            builder.setMessage(message);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    activity.finish();
                    Intent intent = new Intent(activity,LoginPage.class);
                    activity.startActivity(intent);
                }
            });
        }

        else if(code.equals("reg_false"))
        {
            builder.setMessage(message);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    activity.finish();
                }
            });
        }

        else if (code.equals("login_false"))
        {
            builder.setMessage(message);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    EditText loginid,password;
                    loginid = activity.findViewById(R.id.LoginIdNumber);
                    password = activity.findViewById(R.id.LoginPassword);
                    loginid.setText("");
                    password.setText("");

                    dialog.dismiss();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
