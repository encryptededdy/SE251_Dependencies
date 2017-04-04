package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for FanOut
 */
public class QueryFanOut implements Query{
    private CSV _data;
    private NavigableMap<String, Integer> dependantModules = new TreeMap<>();
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : dependantModules.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            System.out.printf("%s   %d\n", entry.getKey(), entry.getValue()); // not sure if tab or space!
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (Map.Entry<String, Module> mod : _data.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>(); // this will store any modules that are a dependency
            for (Dependency dep : mod.getValue().getDependencies()) {
                if (_data.containsKey(dep.getTarget(true)) && !dep.getTarget(true).equals(mod.getKey())) { // check if the dependency matches a module AND isn't the same as the Module we're checking from
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
