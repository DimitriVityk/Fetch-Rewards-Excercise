package com.example.fetchrewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SectionRecyclerAdapter extends RecyclerView.Adapter<ItemEntryViewHolder> {
    List<ListItem> items;

    public SectionRecyclerAdapter(List<ListItem> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ItemEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemEntryViewHolder holder, int position) {

        ListItem item  = items.get(position);

        holder.id.setText("Item ID: " + String.valueOf(item.getId()));
        holder.name.setText("Item Name: " + String.valueOf(item.getName()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
