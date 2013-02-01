package net.avh4.listorganizer.features;

import net.avh4.framework.uilayer.UI;
import net.avh4.listorganizer.ListOrganizer;
import net.avh4.listorganizer.ListSortingModel;

import java.awt.event.KeyEvent;

import static org.fest.assertions.Assertions.assertThat;

public class Agent {
    private final ListSortingModel listSortingModel;
    private final UI ui;

    public Agent(ListOrganizer listOrganizer) {
        this.listSortingModel = listOrganizer.getModel();
        this.ui = listOrganizer.getUi();
    }

    protected void sortNextItem(String item, String group) {
        assertThat(listSortingModel.getUpcomingItems().get(0)).isEqualTo(item);
        int index = findIndexOfGroup(group);
        ui.key(KeyEvent.VK_1 + index);
    }

    private int findIndexOfGroup(String groupName) {
        for (int i = 0; true; i++) {
            if (listSortingModel.getGroups().get(i).getName().equals(groupName)) {
                return i;
            }
        }
    }
}
