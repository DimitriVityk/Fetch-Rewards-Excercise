package com.example.fetchrewards;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemEntryViewHolder extends RecyclerView.ViewHolder {

    TextView id;
    TextView name;

    public ItemEntryViewHolder(@NonNull View itemView) {
        super(itemView);

        id = itemView.findViewById(R.id.itemID);
        name = itemView.findViewById(R.id.itemName);
    }
}
