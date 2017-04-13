package softeng251.dependencies.query;

import softeng251.dependencies.data.CSV;
import softeng251.dependencies.DependenciesException;
import softeng251.dependencies.data.Dependency;
import softeng251.dependencies.data.Module;

import java.util.HashSet;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for Summary
 */

public class Summary implements Query {
    private CSV _data;
    private int _deps = 0;
    private int _srcNoDeps = 0;
    private int _srcWithDeps = 0;
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        // Call methods to grab the data
        int targetNotSrc = countTargetNSrc();
        DepCounter();
        // print the data
        System.out.println("DEPS\t"+_deps);
        System.out.println("SRCWITHDEPS\t"+_srcWithDeps);
        System.out.println("SRCNODEPS\t"+_srcNoDeps);
        System.out.println("TGTNOTSRC\t"+targetNotSrc);
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void DepCounter() {
        for (Module mod : _data.values()) { // loop through modules
            _deps+= mod.size(); // add the number of dependencies the module has to the total count
            if(mod.size() == 0) {
                _srcNoDeps++; // if the module has no dependencies, increment the counter
            } else {
                _srcWithDeps++; // if the module HAS dependencies, increment this counter
            }
        }
    }

    private int countTargetNSrc() {
        HashSet<String> found = new HashSet<>(); // store targets we've found that don't have dependencies

        for (Module mod : _data.values()) { // loop through modules
            for (Dependency dep : mod) { // loop through dependencies
                String target = dep.getTarget(true);
                if (!found.contains(target)) { // if we don't already have the target recorded
                    if (_data.containsKey(target)) { // if we can find the target in our dependency module map
                        if (_data.get(target).size() == 0) {
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
