package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;

import java.util.*;

public class ListSortingModel implements ListSortingView.Model, ListSortingView.Actions {
    private Map<String, Group> groupsByName = new HashMap<>();
    private LinkedList<String> upcomingItems;
    private ArrayList<Group> groups;
    private SortingFinishedListener sortingFinishedListener;

    private void setGroups(List<String> groups) {
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
        if (upcomingItems.isEmpty()) {
            sortingFinishedListener.onSortingFinished(getGroups());
        }
    }

    public void startSorting(List<String> groups, List<String> items, SortingFinishedListener listener) {
        setItems(items);
        setGroups(groups);
        setListener(listener);
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

    private void setItems(List<String> items) {
        upcomingItems = new LinkedList<>(items);
    }

    private void setListener(SortingFinishedListener sortingFinishedListener) {
        this.sortingFinishedListener = sortingFinishedListener;
    }
}
