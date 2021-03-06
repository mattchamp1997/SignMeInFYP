package com.example.signmeinfyp;

import android.util.Log;

import com.example.signmeinfyp.ui.MyModules.ModuleView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpHandler2
{
    private static final String TAG = HttpHandler2.class.getSimpleName();

    public HttpHandler2() {
    }

    public String makeServiceCall(String reqUrl)
    {
        String modID = ModuleView.getIDModule();
        String response = null;

        try
        {
            URL url = new URL(reqUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            OutputStream outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

            String data = URLEncoder.encode("modID", "utf-8") + "=" + URLEncoder.encode(modID, "utf-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            // read the response
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            response = convertStreamToString(in);
        }

        catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        //String nohtml = android.text.Html.fromHtml(response).toString();

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
