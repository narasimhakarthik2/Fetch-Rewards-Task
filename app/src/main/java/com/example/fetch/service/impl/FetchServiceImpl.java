package com.example.fetch.service.impl;

import com.example.fetch.dao.Item;
import com.example.fetch.service.FetchService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchServiceImpl implements FetchService {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void fetchCallAsync(String url, FetchDataCallback callback) {
        executorService.execute(() -> {
            List<Item> result = fetchData(url);
            callback.onDataFetched(result);
        });
    }

    private List<Item> fetchData(String url) {
        OkHttpClient client = new OkHttpClient();
        List<Item> items = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();
                JSONArray jsonArray = new JSONArray(jsonData);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemJson = jsonArray.getJSONObject(i);
                    if (!itemJson.isNull("name") && !itemJson.getString("name").isEmpty()) {
                        Item item = new Item(itemJson.getInt("id"), itemJson.getInt("listId"), itemJson.getString("name"));
                        items.add(item);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return items;
    }
}
