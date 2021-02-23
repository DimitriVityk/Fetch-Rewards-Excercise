package com.example.fetchrewards;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SectionEntryViewHolder extends RecyclerView.ViewHolder {

    TextView sectionHeader;
    RecyclerView sectionRecycler;

    public SectionEntryViewHolder(@NonNull View itemView) {
        super(itemView);

        sectionHeader = itemView.findViewById(R.id.sectionHeader);
        sectionRecycler = itemView.findViewById(R.id.sectionRecycler);
    }
}
