package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.uilayer.Color;
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

    public static final int[] GROUP_HUES = new int[]{0, 100, 200, 300};
    public static final float[] LIST_LIGHTNESS = new float[]{.9f, .85f};
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
        drawGroups(scene, 5);

        scene.add(new ScenePlaceholder(items.get(0),
                ITEM_MARGIN,
                GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN,
                800 - 2 * ITEM_MARGIN,
                CURRENT_ITEM_HEIGHT,
                Color.fromHSL(0, 1f, 1f)));

        int startingY = GROUP_V_MARGIN + GROUP_HEIGHT + GROUP_V_MARGIN + CURRENT_ITEM_HEIGHT + ITEM_MARGIN;
        drawItems(scene, items.subList(1, items.size()), ITEM_MARGIN, startingY, 800 - 2 * ITEM_MARGIN, MORE_ITEMS_HEIGHT, 0.0f, 0);
        return scene;
    }

    private Scene makeSortingFinishedScene() {
        Scene scene = new Scene("Done sorting");
        drawGroups(scene, 1000);
        return scene;
    }

    private void drawGroups(Scene scene, int itemLimit) {
        ImmutableList<Group> groups = model.getGroups();
        int numberOfGroups = groups.size();
        int hueIndex = 0;
        for (int i = 0; i < numberOfGroups; i++) {
            Group group = groups.get(i);
            int x = 800 * i / numberOfGroups;
            int hue = GROUP_HUES[hueIndex % GROUP_HUES.length];
            scene.add(new ScenePlaceholder(group.getName(), x, 0, 800 / numberOfGroups, HEADER_HEIGHT, Color.fromHSL(hue, 1f, .75f)));
            ImmutableList<String> items = group.getItems();
            items = items.subList(Math.max(0, items.size() - itemLimit), items.size());
            drawItems(scene, items, x + ITEM_MARGIN, HEADER_HEIGHT, 800 / numberOfGroups - 2 * ITEM_MARGIN, 20, 0.8f, hue);
            hueIndex++;
        }
    }

    private void drawItems(Scene scene, ImmutableList<String> items, int x, int startingY, int width, int height, float s, int hue) {
        int y = startingY;
        int colorIndex = 0;
        for (String item : items) {
            int color = Color.fromHSL(hue, s, LIST_LIGHTNESS[colorIndex % LIST_LIGHTNESS.length]);
            scene.add(new ScenePlaceholder(item, x, y, width, height, color));
            colorIndex++;
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
