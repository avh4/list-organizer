package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import net.avh4.framework.data.ExternalStorage;
import net.avh4.framework.uilayer.UILayer;
import net.avh4.listorganizer.features.CsvItemsLoader;

import java.util.List;

public abstract class ListOrganizer {
    public static void main(String[] args) {
        ExternalStorage externalStorage = UILayer.getExternalStorage();

        ListSortingModel listSortingModel = new ListSortingModel();
        List<String> groups = ImmutableList.of("Animals", "Mineral", "Vegetable");
        CsvItemsLoader loader = new CsvItemsLoader("goodreads_export.csv", externalStorage);
        List<String> items = loader.getItems();
        listSortingModel.startSorting(groups, items, new SortingFinishedListener() {
            @Override
            public void onSortingFinished(ImmutableList<Group> groups) {
            }
        });
        ListSortingView view = new ListSortingView(listSortingModel, listSortingModel);
        UILayer.main(view);
    }
}
