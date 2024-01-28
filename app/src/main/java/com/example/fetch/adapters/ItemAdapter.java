package com.example.fetch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fetch.R;
import com.example.fetch.dao.Item;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.NestedItemViewHolder> {

    private final List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NestedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new NestedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class NestedItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView IdTextView;

        public NestedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            IdTextView = itemView.findViewById(R.id.IdTextView);

        }

        public void bind(Item item) {
            String nameText = itemView.getContext().getString(R.string.item_name, item.getName());
            String idText = itemView.getContext().getString(R.string.item_id, String.valueOf(item.getId()));
            nameTextView.setText(nameText);
            IdTextView.setText(idText);
        }
    }
}
