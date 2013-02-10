package net.avh4.listorganizer;

import au.com.bytecode.opencsv.CSVReader;
import net.avh4.framework.data.ExternalStorage;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CsvItemsLoader {
    public static final String FILE = "goodreads_export.csv";
    private final ExternalStorage externalStorage;

    public CsvItemsLoader(ExternalStorage externalStorage) {
        this.externalStorage = externalStorage;
    }

    public List<String> getItems() {
        String fileContents = externalStorage.getString(FILE);
        StringReader sr = new StringReader(fileContents);
        CSVReader csv = new CSVReader(sr);
        ArrayList<String> items = new ArrayList<>();
        try {
            csv.readNext();
            while (true) {
                String[] fields = csv.readNext();
                if (fields == null) break;
                items.add(fields[1] + " - " + fields[2]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + FILE, e);
        }
        return items;
    }
}
