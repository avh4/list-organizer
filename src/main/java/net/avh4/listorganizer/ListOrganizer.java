package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.data.ExternalStorage;
import net.avh4.framework.uilayer.UI;
import net.avh4.framework.uilayer.UILayer;
import net.avh4.listorganizer.features.CsvItemsLoader;

import java.util.List;

public class ListOrganizer {
    private final ListSortingModel listSortingModel;
    private final SortingFinishedListener listener;
    private final ListSortingView listSortingView;
    private final CsvItemsLoader itemsLoader;

    public static void main(String[] args) {
        ExternalStorage externalStorage = UILayer.getExternalStorage();
        List<String> groups = ImmutableList.of("Animals", "Mineral", "Vegetable");

        ListOrganizer main = new ListOrganizer(externalStorage);
        main.start(groups);
        UILayer.main(main.getUi());
    }

    public ListOrganizer(ExternalStorage externalStorage) {
        listSortingModel = new ListSortingModel();
        listener = new TextFileWriter(externalStorage);
        itemsLoader = new CsvItemsLoader(externalStorage);
        listSortingView = new ListSortingView(listSortingModel, listSortingModel);
    }

    public void start(List<String> groups, List<String> items) {
        listSortingModel.startSorting(groups, items, listener);
    }

    public void start(List<String> groups) {
        start(groups, itemsLoader.getItems());
    }

    public UI getUi() {
        return listSortingView;
    }

    public ListSortingModel getModel() {
        return listSortingModel;
    }
}
