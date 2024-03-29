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
 * Query handler for FanOut
 */
public class FanOut implements Query{
    private CSV _data;
    private NavigableMap<String, Integer> dependantModules = new TreeMap<>();
    private int _printed = 0;
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : dependantModules.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            System.out.printf("%s\t%d\n", entry.getKey(), entry.getValue());
            _printed++;
        }
        if (_printed == 0) {
            System.out.println("No results");
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (Map.Entry<String, Module> mod : _data.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>(); // this will store any modules that are a dependency of the currently looped module in order to avoid duplicates. Clears every loop
            for (Dependency dep : mod.getValue()) {
                if (!dep.getTarget(true).equals(mod.getKey())) { // check if the dependency isn't the same as the Module we're checking from
                    foundModules.add(dep.getTarget(true)); // We don't need duplicate checking as HashSet doesn't allow duplicates
                }
            }
            String displayName = String.format("%s (%s)", mod.getKey(), mod.getValue().getKind());
            dependantModules.put(displayName, foundModules.size()); // add count of modules depended on by the current module to the map
        }
    }
}
