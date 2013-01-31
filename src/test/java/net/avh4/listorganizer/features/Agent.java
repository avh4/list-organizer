package net.avh4.listorganizer.features;

import net.avh4.listorganizer.ListSortingModel;
import net.avh4.listorganizer.ListSortingView;

import java.awt.event.KeyEvent;

import static org.fest.assertions.Assertions.assertThat;

public class Agent {
    private final ListSortingModel listSortingModel;
    private final ListSortingView listSortingView;

    public Agent(ListSortingModel listSortingModel) {
        this.listSortingModel = listSortingModel;
        this.listSortingView = new ListSortingView(listSortingModel, listSortingModel);
    }

    protected void sortNextItem(String item, String group) {
        assertThat(listSortingModel.getUpcomingItems().get(0)).isEqualTo(item);
        int index = findIndexOfGroup(group);
        listSortingView.key(KeyEvent.VK_1 + index);
    }

    private int findIndexOfGroup(String groupName) {
        for (int i = 0; true; i++) {
            if (listSortingModel.getGroups().get(i).getName().equals(groupName)) {
                return i;
            }
        }
    }
}
