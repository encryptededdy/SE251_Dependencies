package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for FanIn
 */
public class QueryFanIn implements Query {
    private CSV _data;
    private NavigableMap<String, Integer> _dependantModules = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : _dependantModules.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            if (entry.getValue() > 0) {
                System.out.printf("%s\t%d\n", entry.getKey(), entry.getValue());
            }
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (String module : _data.keySet()) {
            _dependantModules.put(module, 0);  // Store all of the modules into an array
        }
        for (Map.Entry<String, Module> entry : _data.entrySet()) { // loop through modules
            HashSet<String> modulesFound = new HashSet<>();
            for (Dependency dep : entry.getValue()) { // go through all dependencies
                String depTarget = dep.getTarget(true);
                if (_dependantModules.containsKey(depTarget) && !modulesFound.contains(depTarget) && !depTarget.equals(entry.getKey())) { // if we find a dependency that's a module
                    modulesFound.add(depTarget);
                    _dependantModules.put(depTarget,_dependantModules.get(depTarget) + 1);
                }
            }
        }
    }
}
