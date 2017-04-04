package softeng251.dependencies;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Edward Zhang on 31/03/2017.
 * Stores package, kind and modifier data for Modules. Maintains an ArrayList of dependencies.
 * The hasIndependent flag indicates whether there is a instance of this module that doesn't have any dependencies
 */

public class Module extends ArrayList<Dependency>{
    private String _pkg;
    private Types.Kind _kind;
    private HashSet<Types.Modifier> _mod = new HashSet<Types.Modifier>();
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
        if (dataarray == null || dataarray.length == 0) { // if we get passed null data, we know that this entry doesn't include dependencies.
            _hasIndependent = true;
        } else {
            _independentOnly = false;
            super.add(new Dependency(dataarray));
        }
    }
    public boolean isIndependent() {
        return _hasIndependent;
    }
    public boolean isIndependentOnly() {
        return _independentOnly;
    }
    public Types.Kind getKind() {
        return _kind;
    }
}
