package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 5/04/2017.
 */
public class QueryStatic implements Query {
    private CSV _data;
    private NavigableMap<String, Integer> staticDeps = new TreeMap<>();
    int printed = 0;
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : staticDeps.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            if (entry.getValue() > 0) {
                printed++;
                System.out.printf("%s\t%d\n", entry.getKey(), entry.getValue()); // print module only if it actually has Uses.
            }
        }
        if (printed == 0) {
            System.out.println("No results");
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (Map.Entry<String, Module> mod : _data.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>();
            for (Dependency dep : mod.getValue()) { // go through the module's dependencies
                if (dep.isCategory("static")) { // check if the dependency is static (if category contains static)
                    foundModules.add(dep.getTarget(true)); // if dependency is static then add it. HashSets by design avoid duplication
                }
            }

            staticDeps.put(String.format("%s (%s)", mod.getKey(), mod.getValue().getKind()), foundModules.size());
        }
    }
}
