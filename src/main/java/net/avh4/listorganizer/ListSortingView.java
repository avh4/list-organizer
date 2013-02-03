package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.uilayer.UI;
import net.avh4.framework.uilayer.scene.Scene;
import net.avh4.framework.uilayer.scene.ScenePlaceholder;

import java.awt.event.KeyEvent;

public class ListSortingView implements UI {

    public static interface Model {
        ImmutableList<Group> getGroups();

        ImmutableList<String> getUpcomingItems();
    }

    public static interface Actions {
        void sort(String item, Group group);
    }

    public static final int GROUP_HEIGHT = 88;
    public static final int MORE_ITEMS_HEIGHT = 20;
    public static final int GROUP_V_MARGIN = 40;
    public static final int CURRENT_ITEM_HEIGHT = 40;
    public static final int HEADER_HEIGHT = 40;
    private static final int ITEM_MARGIN = 10;
    private final Model model;
    private final Actions actions;

    public ListSortingView(Model model, Actions actions) {
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
        drawGroups(scene);

        scene.add(new ScenePlaceholder(items.get(0),
                ITEM_MARGIN,
                GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN,
                800 - 2 * ITEM_MARGIN,
                CURRENT_ITEM_HEIGHT));

        int startingY = GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT;
        drawItems(scene, items.subList(1, items.size()), ITEM_MARGIN, startingY, 800 - 2 * ITEM_MARGIN, MORE_ITEMS_HEIGHT);
        return scene;
    }

    private Scene makeSortingFinishedScene() {
        Scene scene = new Scene("Done sorting");
        drawGroups(scene);
        return scene;
    }

    private void drawGroups(Scene scene) {
        ImmutableList<Group> groups = model.getGroups();
        int numberOfGroups = groups.size();
        for (int i = 0; i < numberOfGroups; i++) {
            Group group = groups.get(i);
            int x = 800 * i / numberOfGroups;
            scene.add(new ScenePlaceholder(group.getName(), x, 0, 800 / numberOfGroups, HEADER_HEIGHT));
            drawItems(scene, group.getItems(), x + ITEM_MARGIN, HEADER_HEIGHT, 800 / numberOfGroups - 2 * ITEM_MARGIN, 20);
        }
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
