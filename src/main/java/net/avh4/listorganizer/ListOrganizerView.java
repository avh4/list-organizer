package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.uilayer.UI;
import net.avh4.framework.uilayer.scene.Scene;
import net.avh4.framework.uilayer.scene.ScenePlaceholder;

import java.awt.event.KeyEvent;

public class ListOrganizerView implements UI {

    public static final int GROUP_HEIGHT = 88;
    public static final int GROUP_WIDTH = 160;
    public static final int MORE_ITEMS_HEIGHT = 20;
    public static final int GROUP_V_MARGIN = 40;
    public static final int CURRENT_ITEM_HEIGHT = 40;
    private final ListOrganizerViewModel model;
    private final ListOrganizerActions actions;

    public ListOrganizerView(ListOrganizerViewModel model, ListOrganizerActions actions) {
        this.model = model;
        this.actions = actions;
    }

    @Override
    public Scene getScene() {
        Scene scene = new Scene();
        ImmutableList<String> groups = model.getGroups();
        scene.add(new ScenePlaceholder(groups.get(0), 120, GROUP_V_MARGIN, GROUP_WIDTH, GROUP_HEIGHT));
        scene.add(new ScenePlaceholder(groups.get(1), 320, GROUP_V_MARGIN, GROUP_WIDTH, GROUP_HEIGHT));
        scene.add(new ScenePlaceholder(groups.get(2), 520, GROUP_V_MARGIN, GROUP_WIDTH, GROUP_HEIGHT));
        ImmutableList<String> items = model.getUpcomingItems();
        scene.add(new ScenePlaceholder(items.get(0), 0, GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN, 800, CURRENT_ITEM_HEIGHT));
        scene.add(new ScenePlaceholder(items.get(1), 0, GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT, 800, MORE_ITEMS_HEIGHT));
        scene.add(new ScenePlaceholder(items.get(2), 0, GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT + MORE_ITEMS_HEIGHT, 800, MORE_ITEMS_HEIGHT));
        scene.add(new ScenePlaceholder(items.get(3), 0, GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT + 2 * MORE_ITEMS_HEIGHT, 800, MORE_ITEMS_HEIGHT));
        scene.add(new ScenePlaceholder(items.get(4), 0, GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT + 3 * MORE_ITEMS_HEIGHT, 800, MORE_ITEMS_HEIGHT));
        return scene;
    }

    @Override
    public void click(int x, int y) {
    }

    @Override
    public void key(int keyCode) {
        int groupIndex = keyCode - KeyEvent.VK_1;
        String group = model.getGroups().get(groupIndex);
        String item = model.getUpcomingItems().get(0);
        actions.sort(item, group);
    }
}
