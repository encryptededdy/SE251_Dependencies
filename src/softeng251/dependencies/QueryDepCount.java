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
    private NavigableMap<Integer, String> dependencies = new TreeMap<Integer, String>();
    private String _filename;
    public void display() {
        if(_datamap == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        printHeader();
        for (Map.Entry<Integer, String> entry : dependencies.descendingMap().entrySet()) { // iterate through the reversed TreeMap (so that it's descending)
            System.out.printf("%s   %d\n", entry.getValue(), entry.getKey()); // not sure if tab or space!
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
        for (Map.Entry<String, Module> entry : _datamap.entrySet()) { // loop through modules
            int depCount = entry.getValue().getDependencies().size();
            String source = entry.getKey();
            String kind = entry.getValue().getKind().toString();
            dependencies.put(depCount, source+" ("+kind+")");
        }
    }
}
