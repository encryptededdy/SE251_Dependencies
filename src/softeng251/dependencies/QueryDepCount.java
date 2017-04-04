package softeng251.dependencies;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for DepCount
 */

public class QueryDepCount implements Query {
    private Map<String, Module> _datamap;
    private NavigableMap<Integer, NavigableMap<String, String>> dependencies = new TreeMap<>();
    private String _filename;
    public void display() {
        if(_datamap == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        printHeader();
        for (NavigableMap<String, String> entry : dependencies.descendingMap().values()) { // iterate through the outer Map (sorts numerically, descending)
            for (String innerentry: entry.values()) { // iterate through the inner Map (this sorts alphabetically)
                System.out.println(innerentry);
            }
        }
    }

    public void setDataSource(CSV data) {
        _datamap = data.getDataMap();
        _filename = data.getFileName();
    }

    private void printHeader() {
        System.out.println("QUERY DepCount");
        System.out.println("DATAID "+_filename);
    }

    private void populateTree() {
        NavigableMap<String, String> innerMap; // create the inner map
        for (Map.Entry<String, Module> entry : _datamap.entrySet()) { // loop through modules
            int depCount = entry.getValue().getDependencies().size();
            String source = entry.getKey();
            String kind = entry.getValue().getKind().toString();
            if (dependencies.containsKey(depCount)) { // if they key is already there
                innerMap = dependencies.get(depCount);
                innerMap.put(source, String.format("%s (%s)    %d", source, kind, depCount));
            } else {
                innerMap = new TreeMap<>();
                innerMap.put(source, String.format("%s (%s)    %d", source, kind, depCount));
                // then add it here
            }
            dependencies.put(depCount, innerMap);
        }
    }
}
