package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;

public class Group {
    private final String name;
    private final ArrayList<String> items = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<String> getItems() {
        return ImmutableList.copyOf(items);
    }

    public void addItem(String item) {
        items.add(item);
    }
}
