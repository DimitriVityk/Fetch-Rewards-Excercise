package com.example.fetchrewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<SectionEntryViewHolder> {

    List<Section> sectionList;
    MainActivity ma;

    public MainRecyclerAdapter(List<Section> sectionList, MainActivity ma)
    {
        this.sectionList = sectionList;
        this.ma = ma;
    }

    @NonNull
    @Override
    public SectionEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_row, parent, false);

        return new SectionEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionEntryViewHolder holder, int position) {
        Section section = sectionList.get(position);
        String sectionName = section.getSectionHeader();
        List<ListItem> itemList = section.getItemList();

        holder.sectionHeader.setText("Item Group Number: " + sectionName);

        SectionRecyclerAdapter sra = new SectionRecyclerAdapter(itemList);
        holder.sectionRecycler.setAdapter(sra);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ma);
        holder.sectionRecycler.setLayoutManager(layoutManager);
        holder.sectionRecycler.addItemDecoration(new DividerItemDecoration(ma, DividerItemDecoration.VERTICAL));
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }
}
