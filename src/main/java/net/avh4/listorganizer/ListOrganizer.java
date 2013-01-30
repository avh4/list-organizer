package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.uilayer.UILayer;

import java.util.*;

public class ListOrganizer implements ListOrganizerViewModel {
    private Map<String, ArrayList<String>> itemsForGroup = new HashMap<>();
    private LinkedList<String> upcomingItems;
    private ImmutableList<String> groups;

    public static void main(String[] args) {
        ListOrganizer model = new ListOrganizer();
        model.setGroups("Animals", "Mineral", "Vegetable");
        model.setItems("Horse", "Calcium", "Man", "Dog", "Carrot", "Pine", "Stone");
        ListOrganizerView view = new ListOrganizerView(model);
        UILayer.main(view, null, null);
    }

    public void setGroups(String... groups) {
        this.groups = ImmutableList.copyOf(groups);
        for (String group : groups) {
            itemsForGroup.put(group, new ArrayList<String>());
        }
    }

    public void sort(String item, String group) {
        itemsForGroup.get(group).add(item);
        upcomingItems.pop();
    }

    public List<String> itemsInGroup(String group) {
        return itemsForGroup.get(group);
    }

    public ImmutableList<String> getGroups() {
        return groups;
    }

    @Override
    public ImmutableList<String> getUpcomingItems() {
        return ImmutableList.copyOf(upcomingItems);
    }

    public void setItems(String... items) {
        upcomingItems = new LinkedList<>(Arrays.asList(items));
    }
}
