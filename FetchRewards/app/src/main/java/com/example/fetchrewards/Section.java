package com.example.fetchrewards;

import java.util.List;

public class Section {

    private String sectionHeader;
    private List<ListItem> itemList;

    public Section(String sectionHeader, List<ListItem> itemList) {
        this.sectionHeader = sectionHeader;
        this.itemList = itemList;
    }

    public String getSectionHeader() {
        return sectionHeader;
    }

    public void setSectionHeader(String sectionHeader) {
        this.sectionHeader = sectionHeader;
    }

    public List<ListItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ListItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionHeader='" + sectionHeader + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
