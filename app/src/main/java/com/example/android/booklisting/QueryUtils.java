package com.example.android.booklisting;

/**
 * Created by vikash on 17/6/17.
 */

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

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    static String LOG_TAG="QueryUtils---";

    /** Sample JSON response for a USGS query */
    public static ArrayList<Book> fetchEarthquakeData(String requestUrl){
        Log.v(LOG_TAG,"---Starting Of fetchWarthquake");
        URL url = createUrl(requestUrl);
        String jsonResponse=null;
        try {
            jsonResponse=makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Book> books = extractEarthquakes(jsonResponse);
        Log.v(LOG_TAG,"----Ending Of fetchWarthquake");

        // Return the {@link Event}
        return books;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl.replace(" ", "%20"));
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";
        if(url==null)
            return jsonResponse;
        HttpURLConnection httpsURLConnection=null;
        InputStream inputStream=null;

        try {
            httpsURLConnection = (HttpURLConnection) url.openConnection();
            httpsURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpsURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            if (httpsURLConnection.getResponseCode() == 200) {
                inputStream = httpsURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpsURLConnection.getResponseCode());
            }
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output=new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link } objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractEarthquakes(String SAMPLE_JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Book> books = new ArrayList<>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonObject = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray items=jsonObject.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject jsonObj = items.getJSONObject(i);
                JSONObject volumeInfo = jsonObj.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");
                String date;
                try{
                    date=volumeInfo.getString("publishedDate");
                }catch (JSONException e){
                    date="NA";
                }

                String url = volumeInfo.getString("canonicalVolumeLink");
                String author_name;
                try{
                    JSONArray authors =volumeInfo.getJSONArray("authors");
                    author_name = authors.getString(0);
                }catch (JSONException e){
                    try {
                        author_name = volumeInfo.getString("publisher");
                    }catch (JSONException e1){
                        author_name="NA";
                    }
                }




                Log.v(LOG_TAG,title);
                Log.v(LOG_TAG,author_name);
                //Log.v(LOG_TAG,url);
                Log.v(LOG_TAG,date);




                books.add(new Book(title,author_name,date,url));

            }

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return books;
    }

}