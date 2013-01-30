package net.avh4.listorganizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOrganizer {
    private Map<String, ArrayList<String>> itemsForGroup = new HashMap<>();

    public void setGroups(String... groups) {
        for (String group : groups) {
            itemsForGroup.put(group, new ArrayList<String>());
        }
    }

    public void sort(String item, String group) {
        itemsForGroup.get(group).add(item);
    }

    public List<String> itemsInGroup(String group) {
        return itemsForGroup.get(group);
    }
}
