package com.example.fetch.adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetch.R;
import com.example.fetch.dao.Item;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.ItemViewHolder> {

    private final Map<Integer, List<Item>> items;
    private final SparseBooleanArray expandedState;

    public GroupItemAdapter(Map<Integer, List<Item>> items) {
        this.items = items;
        this.expandedState = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        List<Item> itemList = new ArrayList<>(items.get(position + 1)); // Adjust for 0-based index
        holder.bind(itemList.get(0)); // Assuming each group has at least one item

        // Set click listener for the group card
        holder.groupCard.setOnClickListener(view -> toggleExpansion(position));

        // Handle expansion and set data for nested RecyclerView
        boolean isExpanded = expandedState.get(position, false);
        holder.arrowIcon.setRotation(isExpanded ? 180f : 0f);
        holder.nestedRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        if (isExpanded) {
            holder.setupNestedRecyclerView(itemList);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    private void toggleExpansion(int position) {
        boolean isExpanded = expandedState.get(position, false);
        expandedState.put(position, !isExpanded);
        notifyItemChanged(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTv;
        private final MaterialCardView groupCard;
        private final ImageView arrowIcon;
        private final RecyclerView nestedRecyclerView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.titleTv);
            groupCard = itemView.findViewById(R.id.groupCard);
            arrowIcon = itemView.findViewById(R.id.arrowIcon);
            nestedRecyclerView = itemView.findViewById(R.id.nestedRecyclerView);
        }

        public void bind(Item item) {
            String listIdText = itemView.getContext().getString(R.string.list_id, String.valueOf(item.getListId()));
            titleTv.setText(listIdText);
        }
        public void setupNestedRecyclerView(List<Item> itemList) {
            // Set up your nested RecyclerView here (similar to how you set up the main RecyclerView)
            // Use a different adapter for the nested RecyclerView if needed.
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            nestedRecyclerView.setLayoutManager(layoutManager);
            // Set adapter for nested RecyclerView
            nestedRecyclerView.setAdapter(new ItemAdapter(itemList));
        }
    }
}
