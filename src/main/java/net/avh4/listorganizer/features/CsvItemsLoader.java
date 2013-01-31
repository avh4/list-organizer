package net.avh4.listorganizer.features;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvItemsLoader {
    public List<String> createItemsFromCsv(String filename) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        Reader inputStream = new InputStreamReader(is);
        CSVReader csv = new CSVReader(inputStream);
        ArrayList<String> items = new ArrayList<>();
        try {
            csv.readNext();
            while (true) {
                String[] fields = csv.readNext();
                if (fields == null) break;
                items.add(fields[1] + " - " + fields[2]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + filename, e);
        }
        return items;
    }
}
