package softeng251.dependencies;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Edward Zhang on 31/03/2017.
 * Stores package, kind and modifier data for Modules. Maintains an ArrayList of dependencies.
 * The hasIndependent flag indicates whether there is a instance of this module that doesn't have any dependencies
 */

public class Module {
    private String _pkg;
    private Types.Kind _kind;
    private HashSet<Types.Modifier> _mod = new HashSet<Types.Modifier>();
    private ArrayList<Dependency> _dependencyList = new ArrayList<Dependency>();
    private boolean _hasIndependent = false;
    private boolean _independentOnly = true;

    public Module(String pkg, String kind, String mod) {
        _pkg = pkg;
        _kind = Types.Kind.valueOf(kind.toUpperCase());
        String[] modArray = mod.split(","); // split out the modifiers
        for (String currentMod : modArray) {
            try {
                if (!currentMod.equals("")) { // only insert a modifier if there it isn't empty
                    _mod.add(Types.Modifier.valueOf(currentMod.toUpperCase())); // put these into a HashSet
                }
            } catch (IllegalArgumentException e) {
                throw new DependenciesException("Invalid / Unimplemented Modifier Type (" + currentMod + ")", e);
            }
        }
    }
    public void add(String[] dataarray) {
        _independentOnly = false;
        _dependencyList.add(new Dependency(dataarray));
    }
    public void setIndependent() { // if we find that there is a instance of this module that doesn't have dependencies
        _hasIndependent = true;
    }
    public boolean isIndependent() {
        return _hasIndependent;
    }
    public boolean isIndependentOnly() {
        return _independentOnly;
    }
    public ArrayList<Dependency> getDependencies() {
        return _dependencyList;
    }
}
