package com.annekay.android.androiddevelopers;

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

/**
 * Created by HP on 8/23/2017.
 */

public class QueryUtil {

    public static final String LOG_TAG = QueryUtil.class.getSimpleName();

    private QueryUtil() {
    }

    /**
     * Return a list of {@link Developer} objects that has been built up from
     * parsing a JSON response.
     */

    public static List<Developer> fetchDeveloperData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrlFromString(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Developer> developers = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return developers;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
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
     * Return an {@link Developer} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static List<Developer> extractFeatureFromJson(String developerJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(developerJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Developer> developers = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Extract the JSONArray associated with the key called "items",

            JSONObject response = new JSONObject(developerJSON);
            JSONArray developersArray = response.getJSONArray("items");

            for (int i = 0; i < developersArray.length(); i++) {
                JSONObject currentDeveloper = developersArray.getJSONObject(i);

                // Extract the value for the key called "avatar_url"
                String thumbNailUrl = currentDeveloper.getString("avatar_url");

                // Extract the value for the key called "login"
                String userName = currentDeveloper.getString("login");

                // Extract the value for the key called "html_url"
                String gitHubUrl = currentDeveloper.getString("html_url");
                // Create a new object with the developer items
                Developer developer = new Developer(thumbNailUrl, userName, gitHubUrl);
                // adding developer to developer's array
                developers.add(developer);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the developer JSON results", e);
        }

        // Return the list of earthquakes
        return developers;
    }
    private static URL createUrlFromString(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}
