package softeng251.dependencies;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for FanIn
 */
public class QueryFanIn implements Query {
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
        for (Map.Entry<String, Module> mod : _data.entrySet()) {
            String currentSrc = mod.getKey();
            int uses = 0;
            for (Map.Entry<String, Module> entry : _data.entrySet()) { // loop through modules
                if (!currentSrc.equals(entry.getKey())) { // If we're not on the same module.
                    for (Dependency dep : entry.getValue().getDependencies()) { // go through all dependencies
                        if (dep.getTarget(true).equals(currentSrc)) { // if we find a dependency that's the same as our current module
                            uses++; // count it!
                            break; // to avoid double counting
                        }
                    }
                }
            }
            if (uses > 0) {
                String kind = mod.getValue().getKind().toString();
                dependantModules.put(currentSrc + " (" + kind + ")", uses);
            }
        }
    }
}
