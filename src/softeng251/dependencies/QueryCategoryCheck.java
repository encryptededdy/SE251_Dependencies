package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 5/04/2017.
 * Responds to the Static query
 */
public class QueryCategoryCheck implements Query {
    private CSV _data;
    private NavigableMap<String, Integer> moduleData = new TreeMap<>();
    private int _printed = 0;
    private String[] _categoryToCheck;
    public QueryCategoryCheck(String[] categoryToCheck) {
        _categoryToCheck = categoryToCheck; // if input is array then set directly
    }
    public QueryCategoryCheck(String categoryToCheck) {
        _categoryToCheck = new String[]{categoryToCheck}; // if input is single string then put it in a single cell array
    }
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : moduleData.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            if (entry.getValue() > 0) {
                _printed++;
                System.out.printf("%s\t%d\n", entry.getKey(), entry.getValue()); // print module only if it actually has Uses.
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
        for (Map.Entry<String, Module> mod : _data.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>();
            for (Dependency dep : mod.getValue()) { // go through the module's dependencies
                for (String category : _categoryToCheck) { // loop through the specified categories
                    if (dep.isCategory(category)) { // check if the dependency is of the specified category
                        foundModules.add(dep.getTarget(true)); // if dependency is of the specified category then add it. HashSets by design avoid duplication
                    }
                }
            }

            moduleData.put(String.format("%s (%s)", mod.getKey(), mod.getValue().getKind()), foundModules.size());
        }
    }
}
