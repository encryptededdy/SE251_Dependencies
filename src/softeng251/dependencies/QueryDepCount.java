package softeng251.dependencies;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for DepCount
 */

public class QueryDepCount implements Query {
    private CSV _data;
    private NavigableMap<Integer, NavigableMap<String, String>> dependencies = new TreeMap<>();
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (NavigableMap<String, String> entry : dependencies.descendingMap().values()) { // iterate through the outer Map (sorts numerically, descending)
            for (String innerentry: entry.values()) { // iterate through the inner Map (this sorts alphabetically)
                System.out.println(innerentry);
            }
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        NavigableMap<String, String> innerMap; // create the inner map
        for (Map.Entry<String, Module> entry : _data.entrySet()) { // loop through modules
            int depCount = entry.getValue().size();
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
