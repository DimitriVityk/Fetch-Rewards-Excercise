package com.example.fetchrewards;

public class ListItem implements Comparable<ListItem>{

    private int id;
    private int listId;
    private String name;

    public ListItem(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(ListItem o) {
        String [] thisSplit = name.split(" ");
        String [] thatSplit = o.getName().split(" ");
        Integer thisInt = Integer.valueOf(thisSplit[1]);
        Integer thatInt = Integer.parseInt(thatSplit[1]);
        return thisInt.compareTo(thatInt);
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "id=" + id +
                ", listId=" + listId +
                ", name='" + name + '\'' +
                '}';
    }
}
