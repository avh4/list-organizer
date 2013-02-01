package net.avh4.listorganizer;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import net.avh4.framework.data.ExternalStorage;
import net.avh4.framework.data.ExternalStorageException;

import javax.swing.*;

public class TextFileWriter implements SortingFinishedListener {
    private final ExternalStorage externalStorage;

    public TextFileWriter(ExternalStorage externalStorage) {
        this.externalStorage = externalStorage;
    }

    @Override
    public void onSortingFinished(ImmutableList<Group> groups) {
        for (Group group : groups) {
            String data = Joiner.on("\n").join(group.getItems());
            if (!group.getItems().isEmpty()) {
                data += "\n";
            }
            try {
                externalStorage.writeFile(group.getName() + ".txt", data);
            } catch (ExternalStorageException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            }
        }
    }
}
