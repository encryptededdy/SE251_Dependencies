package softeng251.dependencies;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for Summary
 */

public class QuerySummary implements Query {
    private Map<String, Module> _datamap;
    private int _deps;
    private String _filename;
    public void display() {
        if(_datamap == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        int srcWithDeps = _datamap.size();
        int srcNoDeps = countNoDeps();
        int targetNotSrc = countTargetNSrc();
        // print the data
        printHeader();
        System.out.println("DEPS "+_deps);
        System.out.println("SRCWITHDEPS "+srcWithDeps);
        System.out.println("SRCNODEPS "+srcNoDeps);
        System.out.println("TGTNOTSRC "+targetNotSrc);
    }

    public void setDataSource(CSV data) {
        _datamap = data.getDataMap();
        _deps = data.getDepCount();
        _filename = data.getFileName();
    }

    private void printHeader() {
        System.out.println("QUERY Summary");
        System.out.println("DATAID "+_filename);
    }

    private int countNoDeps() {
        int count = 0;
        for (Module mod : _datamap.values()) {
            if(mod.isIndependent()) {
                count++;
            }
        }
        return count;
    }

    private int countTargetNSrc() {
        HashSet<String> found = new HashSet<String>(); // store targets we've found that don't have dependencies

        for (Module mod : _datamap.values()) { // loop through modules
            for (Dependency dep : mod.getDependencies()) { // loop through dependencies
                String target = dep.getTarget(true); // TODO: Not sure how the suffixes affect things. Ignoring them for now.
                if (!found.contains(target)) { // if we don't already have the target recorded
                    if (_datamap.containsKey(target)) { // if we can find the target in our dependency module map
                        if (_datamap.get(target).isIndependentOnly()) {
                            found.add(target); // if we find it in one of the modules but it reports that it doesn't contain any dependencies, then we add it too.
                        }
                    } else { // we can't find the target
                        found.add(target); // therefore the target has no dependencies. Add it to the found set.
                    }
                }
            }
        }
        return found.size(); // return the number of targets we've found
    }
}
