package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.uilayer.UILayer;

import java.util.*;

public class ListOrganizer implements ListOrganizerViewModel, ListOrganizerActions {
    private Map<String, Group> groupsByName = new HashMap<>();
    private LinkedList<String> upcomingItems;
    private ArrayList<Group> groups;

    public static void main(String[] args) {
        ListOrganizer listOrganizer = new ListOrganizer();
        listOrganizer.setGroups("Animals", "Mineral", "Vegetable");
        listOrganizer.setItems("Horse", "Calcium", "Man", "Dog", "Carrot", "Pine", "Stone");
        ListOrganizerView view = new ListOrganizerView(listOrganizer, listOrganizer);
        UILayer.main(view);
    }

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
