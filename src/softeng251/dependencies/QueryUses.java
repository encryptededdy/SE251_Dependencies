package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 4/04/2017.
 * Responds to the Uses query
 */
public class QueryUses implements Query {
    private CSV _data;
    private NavigableMap<String, Integer> moduleUses = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : moduleUses.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            if (entry.getValue() > 0) {
                System.out.printf("%s\t%d\n", entry.getKey(), entry.getValue()); // print module only if it actually has Uses.
            }
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (Map.Entry<String, Module> mod : _data.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>();
            for (Dependency dep : mod.getValue()) { // go through the module's dependencies
                if (dep.isCategory("invoke") || dep.isCategory("field")) { // check if the dependency is used by the module (if category contains invoke or field)
                    foundModules.add(dep.getTarget(true)); // if dependency is used then add it. HashSets by design avoid duplication
                }
            }

            moduleUses.put(String.format("%s (%s)", mod.getKey(), mod.getValue().getKind()), foundModules.size());
        }
    }
}
