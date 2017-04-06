package softeng251.dependencies.query;

import softeng251.dependencies.data.CSV;
import softeng251.dependencies.DependenciesException;
import softeng251.dependencies.data.Dependency;
import softeng251.dependencies.data.Module;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for FanIn
 */
public class FanIn implements Query {
    private CSV _data;
    private NavigableMap<String, Integer> _dependantModules = new TreeMap<>();
    private int _printed = 0;
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : _dependantModules.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            if (entry.getValue() > 0) { // only print if there actually are values
                String displayName = String.format("%s (%s)", entry.getKey(), _data.get(entry.getKey()).getKind());
                System.out.printf("%s\t%d\n", displayName, entry.getValue());
                _printed++;
            }
        }
        if (_printed == 0) {
            System.out.println("No results");
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (String module : _data.keySet()) {
            _dependantModules.put(module, 0);  // Store all of the modules that have been seen into an array
        }
        for (Map.Entry<String, Module> entry : _data.entrySet()) { // loop through modules
            HashSet<String> modulesFound = new HashSet<>(); // keep track of the dependencies we've added in this module (as we only count one dependency per target module)
            for (Dependency dep : entry.getValue()) { // go through all dependencies
                String depTarget = dep.getTarget(true);
                if (_dependantModules.containsKey(depTarget) && !modulesFound.contains(depTarget) && !depTarget.equals(entry.getKey())) { // if we find a dependency that's a seen module, hasn't already been added for this source module and is not the same as the source module
                    modulesFound.add(depTarget); // record in modulesFound that we've found a dependency for this module (this is used to prevent duplicate entries)
                    _dependantModules.put(depTarget,_dependantModules.get(depTarget) + 1); // increment the counter for the appropriate module indicating that we've found a new dependency
                }
            }
        }
    }
}
