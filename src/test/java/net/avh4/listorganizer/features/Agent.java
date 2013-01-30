package net.avh4.listorganizer.features;

import net.avh4.listorganizer.ListOrganizer;
import net.avh4.listorganizer.ListOrganizerView;

import java.awt.event.KeyEvent;

import static org.fest.assertions.Assertions.assertThat;

public class Agent {
    private final ListOrganizer listOrganizer;
    private final ListOrganizerView listOrganizerView;

    public Agent(ListOrganizer listOrganizer) {
        this.listOrganizer = listOrganizer;
        this.listOrganizerView = new ListOrganizerView(listOrganizer, listOrganizer);
    }

    protected void sortNextItem(String item, String group) {
        assertThat(listOrganizer.getUpcomingItems().get(0)).isEqualTo(item);
        assertThat(listOrganizer.getGroups()).contains(group);
        int index = listOrganizer.getGroups().indexOf(group);
        listOrganizerView.key(KeyEvent.VK_1 + index);
    }
}
