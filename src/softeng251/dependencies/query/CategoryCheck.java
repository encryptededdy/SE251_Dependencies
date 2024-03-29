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
 * Created by Edward Zhang on 5/04/2017.
 * Responds to the Static query
 */
public class CategoryCheck implements Query {
    private CSV _data;
    private NavigableMap<String, Integer> moduleData = new TreeMap<>();
    private int _printed = 0;
    private String[] _categoryToCheck;
    public CategoryCheck(String[] categoryToCheck) {
        _categoryToCheck = categoryToCheck; // if input is array then set directly
    }
    public CategoryCheck(String categoryToCheck) {
        _categoryToCheck = new String[]{categoryToCheck}; // if input is single string then put it in a single cell array
    }
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (Map.Entry<String, Integer> entry : moduleData.entrySet()) { // iterate through the TreeMap and print (it's already sorted)
            _printed++;
            System.out.printf("%s\t%d\n", entry.getKey(), entry.getValue()); // print module only if it actually has Uses.
        }
        if (_printed == 0) {
            System.out.println("No results"); // if we printed no data,
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (Map.Entry<String, Module> mod : _data.entrySet()) { // go through every module
            HashSet<String> foundModules = new HashSet<>(); // foundModule stores the dependencies that match the category in this specific module (to avoid double counting)
            for (Dependency dep : mod.getValue()) { // go through the module's dependencies
                for (String category : _categoryToCheck) { // loop through the specified categories
                    if (dep.isCategory(category) && !dep.getTarget(true).equals(mod.getKey())) { // check if the dependency is of the specified category and isn't a self dependency
                        foundModules.add(dep.getTarget(true)); // if dependency is of the specified category then add it. HashSets by design avoid duplication
                    }
                }
            }

            moduleData.put(String.format("%s (%s)", mod.getKey(), mod.getValue().getKind()), foundModules.size()); // the size of the hashset is the number of values (since it deduplicates for us)
        }
    }
}
