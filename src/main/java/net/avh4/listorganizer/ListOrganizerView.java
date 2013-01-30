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
    public static final int HEADER_HEIGHT = 40;
    private final ListOrganizerViewModel model;
    private final ListOrganizerActions actions;

    public ListOrganizerView(ListOrganizerViewModel model, ListOrganizerActions actions) {
        this.model = model;
        this.actions = actions;
    }

    @Override
    public Scene getScene() {

        ImmutableList<String> items = model.getUpcomingItems();

        if (items.isEmpty()) {
            return makeSortingFinishedScene();
        } else {
            return makeSortingScene();
        }

    }

    private Scene makeSortingScene() {
        ImmutableList<String> items = model.getUpcomingItems();
        Scene scene = new Scene("Sorting");
        ImmutableList<Group> groups = model.getGroups();
        scene.add(new ScenePlaceholder(groups.get(0).getName(), 120, GROUP_V_MARGIN, GROUP_WIDTH, GROUP_HEIGHT));
        scene.add(new ScenePlaceholder(groups.get(1).getName(), 320, GROUP_V_MARGIN, GROUP_WIDTH, GROUP_HEIGHT));
        scene.add(new ScenePlaceholder(groups.get(2).getName(), 520, GROUP_V_MARGIN, GROUP_WIDTH, GROUP_HEIGHT));

        scene.add(new ScenePlaceholder(items.get(0), 0, GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN, 800, CURRENT_ITEM_HEIGHT));

        int startingY = GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT;
        drawItems(scene, items.subList(1, items.size()), 0, startingY, 800, MORE_ITEMS_HEIGHT);
        return scene;
    }

    private Scene makeSortingFinishedScene() {
        Scene scene = new Scene("Done sorting");
        ImmutableList<Group> groups = model.getGroups();
        for (int i = 0; i < 3; i++) {
            Group group = groups.get(i);
            int x = 800 * i / 3;
            scene.add(new ScenePlaceholder(group.getName(), x, 0, 800 / 3, HEADER_HEIGHT));
            drawItems(scene, group.getItems(), x, HEADER_HEIGHT, 800 / 3, 20);
        }
        return scene;
    }

    private void drawItems(Scene scene, ImmutableList<String> items, int x, int startingY, int width, int height) {
        int y = startingY;
        for (String item : items) {
            scene.add(new ScenePlaceholder(item, x, y, width, height));
            y += height;
        }
    }

    @Override
    public void click(int x, int y) {
    }

    @Override
    public void key(int keyCode) {
        int groupIndex = keyCode - KeyEvent.VK_1;
        if (groupIndex < 0) return;
        if (groupIndex >= model.getGroups().size()) return;
        Group group = model.getGroups().get(groupIndex);
        String item = model.getUpcomingItems().get(0);
        actions.sort(item, group);
    }
}
