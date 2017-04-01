package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 */
public class QueryFanOut implements Query{
    private Map<String, Module> _datamap;
    private NavigableMap<String, Integer> dependantModules = new TreeMap<>();
    private String _filename;
    public void display() {
        if(_datamap == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        printHeader();
        for (Map.Entry<String, Integer> entry : dependantModules.entrySet()) { // iterate through the reversed TreeMap (so that it's descending)
            System.out.printf("%s   %d\n", entry.getKey(), entry.getValue()); // not sure if tab or space!
        }
    }

    public void setDataSource(CSV data) {
        _datamap = data.getDataMap();
        _filename = data.getFileName();
    }

    private void printHeader() {
        System.out.println("QUERY FanOut");
        System.out.println("DATAID "+_filename);
    }

    private void populateTree() {
        for (Map.Entry<String, Module> mod : _datamap.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>(); // this will store any modules that are a dependency
            for (Dependency dep : mod.getValue().getDependencies()) {
                if (_datamap.containsKey(dep.getTarget(true)) && !dep.getTarget(true).equals(mod.getKey())) { // check if the dependency matches a module AND isn't the same as the Module we're checking from
                    foundModules.add(dep.getTarget(true)); // We don't need duplicate checking as HashSet doesn't allow duplicates
                }
            }
            if (foundModules.size() > 0) {
                dependantModules.put(mod.getKey(), foundModules.size()); // if we found some dependant modules add them to the Map
            }
            foundModules.clear(); // empty the set for next loop
        }
    }
}
