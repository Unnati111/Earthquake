package com.example.ujjawaljain.earthquake;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.ujjawaljain.earthquake.EarthquakeActivity.LOG_TAG;

public  final class QueryUtils {


    private QueryUtils() {
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
      URL url=createUrl(requestUrl);
      String jsonResponse= null;
      try{
          jsonResponse= makeHttpRequest(url);
      }catch (IOException e) {
        Log.e(LOG_TAG,"problem making the http request",e);
      }
      List<Earthquake> earthquakes= extractFeatureFromJson(jsonResponse);
      return  earthquakes;

    }


    private static URL createUrl(String stringUrl){
        URL url=null;
        try{
            url=new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Problem in url",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url)throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            //if connection successful then response code is 200
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem retrieving json results",e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream !=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output= new StringBuilder();
        if (inputStream !=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON){
        if(TextUtils.isEmpty(earthquakeJSON)){
            return  null;
        }
        List<Earthquake> earthquakes=new ArrayList<>();
        try{
            JSONObject baseJsonResponse= new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray= baseJsonResponse.getJSONArray("features");

            for(int i=0;i<earthquakeArray.length();i++){
                JSONObject currentEarthquake=earthquakeArray.getJSONObject(i);
                JSONObject properties= currentEarthquake.getJSONObject("properties");

                double magnitude= properties.getDouble("mag");
                String location= properties.getString("place");
                long time=properties.getLong("time");
                String url=properties.getString("url");

                Earthquake earthquake=new Earthquake(magnitude,location,time,url);
                earthquakes.add(earthquake);
            }

        }catch (JSONException e){
            Log.e("QueryUtils","problem parsing",e);
        }
        return earthquakes;
    }




}
