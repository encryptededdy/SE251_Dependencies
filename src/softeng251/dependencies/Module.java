package softeng251.dependencies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Edward Zhang on 31/03/2017.
 * Stores the dependencies for modules
 */

public class Module {
    private String _pkg;
    private Types.Kind _kind;
    private HashSet<String> _mod = new HashSet<String>();
    private boolean _noDep;
    private ArrayList<Dependency> _dependencyList = new ArrayList<Dependency>();

    public Module(String pkg, String kind, String mod, boolean noDep) {
        _pkg = pkg;
        _kind = Types.Kind.valueOf(kind);
        String[] modArray = mod.split(","); // split out the modifiers
        _mod = new HashSet<>(Arrays.asList(modArray)); // put these into a HashArray
        _noDep = noDep;
    }
    public void add(String[] dataarray) {
        if (_noDep) {
            throw new DependenciesException("Attempted to add dependency to Module marked noDep");
        } else {
            _dependencyList.add(new Dependency(dataarray));
        }
    }
}
