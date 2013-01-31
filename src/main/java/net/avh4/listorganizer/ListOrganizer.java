package net.avh4.listorganizer;

import net.avh4.framework.uilayer.UILayer;

public abstract class ListOrganizer {
    public static void main(String[] args) {
        ListSortingModel listSortingModel = new ListSortingModel();
        listSortingModel.setGroups("Animals", "Mineral", "Vegetable");
        listSortingModel.setItems("Horse", "Calcium", "Man", "Dog", "Carrot", "Pine", "Stone");
        ListSortingView view = new ListSortingView(listSortingModel, listSortingModel);
        UILayer.main(view);
    }
}
