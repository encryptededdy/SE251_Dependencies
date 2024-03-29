package softeng251.dependencies.data;

import softeng251.dependencies.DependenciesException;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Edward Zhang on 31/03/2017.
 * Stores package, kind and modifier data for Modules. Maintains an ArrayList of dependencies.
 */

public class Module extends ArrayList<Dependency>{
    private Types.Kind _kind; // stores the kind of the module using the Types.Kind enum

    // Modifier data processing implemented but not used for the purposes of this Assignment
    /* private HashSet<Types.Modifier> _mod = new HashSet<Types.Modifier>(); */

    public Module(String kind) {
        _kind = Types.Kind.valueOf(kind); // converts the input kind String into a Kind enum

        // Modifier data processing implemented but not used for the purposes of this Assignment
        /*String[] modArray = mod.split(","); // split out the modifiers
        for (String currentMod : modArray) {
            try {
                if (!currentMod.equals("")) { // only insert a modifier if there it isn't empty
                    _mod.add(Types.Modifier.valueOf(currentMod.toUpperCase())); // put these into a HashSet
                }
            } catch (IllegalArgumentException e) {
                throw new DependenciesException("Invalid / Unimplemented Modifier Type (" + currentMod + ")", e);
            }
        }*/
    }
    public void add(String[] dataarray) { // overload add with this method which will convert string arrays into Dependency objects before storage
        super.add(new Dependency(dataarray));
    }
    public Types.Kind getKind() {
        return _kind;
    }
}
