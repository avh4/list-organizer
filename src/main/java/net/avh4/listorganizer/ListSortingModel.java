package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;

import java.util.*;

public class ListSortingModel implements ListSortingViewModel, ListSortingActions {
    private Map<String, Group> groupsByName = new HashMap<>();
    private LinkedList<String> upcomingItems;
    private ArrayList<Group> groups;

    public void setGroups(String... groups) {
        this.groups = new ArrayList<>();
        for (String groupName : groups) {
            Group group = new Group(groupName);
            groupsByName.put(groupName, group);
            this.groups.add(group);
        }
    }

    @Override
    public void sort(String item, Group group) {
        group.addItem(item);
        upcomingItems.pop();
    }

    public List<String> itemsInGroup(String groupName) {
        return groupsByName.get(groupName).getItems();
    }

    public ImmutableList<Group> getGroups() {
        return ImmutableList.copyOf(groups);
    }

    @Override
    public ImmutableList<String> getUpcomingItems() {
        return ImmutableList.copyOf(upcomingItems);
    }

    public void setItems(String... items) {
        upcomingItems = new LinkedList<>(Arrays.asList(items));
    }

    public void setItems(List<String> items) {
        upcomingItems = new LinkedList<>(items);
    }
}
