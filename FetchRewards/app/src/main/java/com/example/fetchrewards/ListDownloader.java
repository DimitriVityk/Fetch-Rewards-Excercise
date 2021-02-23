package com.example.fetchrewards;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.net.HttpURLConnection.HTTP_OK;

public class ListDownloader implements Runnable {

    private static final String baseURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    private static final String TAG = "ListDownloader";
    private MainActivity mainActivity;

    public ListDownloader(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        Uri.Builder uriBuilder = Uri.parse(baseURL).buildUpon();
        String urlToUse = uriBuilder.toString();

        try {
            URL url = new URL(urlToUse);
            Log.d(TAG, "url:  " + url.toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if(responseCode == HTTP_OK)
            {
                Log.d(TAG, "run: HTTP ResponseCode OK: " + connection.getResponseCode());
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while(null != (line = reader.readLine()))
                {
                    result.append(line).append("\n");
                }
                process(result.toString());
            } else {
                Log.d(TAG, "run: HTTP ResponseCode NOT OK: " + connection.getResponseCode());
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            }

        } catch (Exception e) {

        } finally {
            if (connection != null)
            {
                connection.disconnect();
            }
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream: " + e.getMessage());
                }
            }
        }
    }

    public void process (String result)
    {
        Map<Integer, List<ListItem>> treeMap = new TreeMap<>();

        try {

            JSONArray jsonArr = new JSONArray(result);

            for(int i = 0; i < jsonArr.length(); i++)
            {
                ListItem listItem;
                JSONObject jsonItem = (JSONObject) jsonArr.get(i);
                String name = jsonItem.isNull("name")? null : jsonItem.getString("name");
                if(name != null && !name.equals(""))
                {
                    int id = jsonItem.getInt("id");
                    int listId = jsonItem.getInt("listId");
                    listItem = new ListItem(id, listId, name);
                    if(treeMap.containsKey(listItem.getListId()))
                    {
                        List<ListItem> mapList = treeMap.get(listItem.getListId());
                        mapList.add(listItem);
                    }
                    else
                    {
                        List<ListItem> newList = new ArrayList<>();
                        newList.add(listItem);
                        treeMap.put(listItem.getListId(), newList);
                    }
                }
            }

            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.loadItemMap(treeMap);
                }
            });

        } catch (JSONException e)
        {
            e.printStackTrace();
        }



    }
}
