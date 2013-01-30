package net.avh4.listorganizer.features;

import net.avh4.listorganizer.ListOrganizer;

import static org.fest.assertions.Assertions.assertThat;

public class Agent {
    protected final ListOrganizer listOrganizer;

    public Agent(ListOrganizer listOrganizer) {
        this.listOrganizer = listOrganizer;
    }

    protected void sortNextItem(String item, String group) {
        assertThat(listOrganizer.getUpcomingItems().get(0)).isEqualTo(item);
        assertThat(listOrganizer.getGroups()).contains(group);
        listOrganizer.sort(item, group);
    }
}
