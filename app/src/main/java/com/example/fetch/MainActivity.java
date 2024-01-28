package com.example.fetch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.fetch.adapters.GroupItemAdapter;
import com.example.fetch.dao.Item;
import com.example.fetch.service.FetchService;
import com.example.fetch.service.impl.FetchServiceImpl;
import com.example.fetch.util.ItemSorter;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroupItemAdapter adapter;
    // Inside your MainActivity
    private View loadingProgressBar;
    private View emptyDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ProgressBar and TextView
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        emptyDataTextView = findViewById(R.id.emptyDataTextView);

        FetchService fetchService = new FetchServiceImpl();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        fetchService.fetchCallAsync(URL, items -> {
            Map<Integer, List<Item>> groupedItems = ItemSorter.groupAndSortItems(items);

            runOnUiThread(() -> {
                // Show loading progress while data is being fetched
                loadingProgressBar.setVisibility(View.GONE);

                if (!groupedItems.isEmpty()) {
                    adapter = new GroupItemAdapter(groupedItems);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Show a message when data is empty
                    emptyDataTextView.setVisibility(View.VISIBLE);
                }
            });
        });
    }
}