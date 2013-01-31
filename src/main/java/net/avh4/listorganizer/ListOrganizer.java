package net.avh4.listorganizer;

import net.avh4.framework.data.ExternalStorage;
import net.avh4.framework.uilayer.UILayer;
import net.avh4.listorganizer.features.CsvItemsLoader;

public abstract class ListOrganizer {
    public static void main(String[] args) {
        ExternalStorage externalStorage = UILayer.getExternalStorage();

        ListSortingModel listSortingModel = new ListSortingModel();
        listSortingModel.setGroups("Animals", "Mineral", "Vegetable");
        CsvItemsLoader loader = new CsvItemsLoader("goodreads_export.csv", externalStorage);
        listSortingModel.setItems(loader.getItems());
        ListSortingView view = new ListSortingView(listSortingModel, listSortingModel);
        UILayer.main(view);
    }
}
