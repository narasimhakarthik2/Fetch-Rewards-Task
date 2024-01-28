package com.example.fetch.service;

import com.example.fetch.dao.Item;

import java.util.List;

public interface FetchService {

    interface FetchDataCallback {
        void onDataFetched(List<Item> items);
    }

    void fetchCallAsync(String url, FetchDataCallback callback);
}
