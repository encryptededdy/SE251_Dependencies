package softeng251.dependencies.data;

import softeng251.dependencies.DependenciesException;

/**
 * Created by Edward Zhang on 31/03/2017.
 * Stores dependency information for a Module
 */

public class Dependency {
    private String _target;
    private String _category;
    // The following fields were defined but are not used for the purposes of this assignment. Removed to save memory.
    /* private String _package;
    private Types.Kind _kind;
    private String _area;
    private String _details;
    private String _locations;
    private String _depTarget;
    // Visibility and Inheritance is not needed for this assignment
    private boolean _toSelf; */

    public Dependency(String[] dataarray) { // Constructor stores the information about the dependency into fields
        _target = dataarray[0];
        _category = dataarray[3];
        // The following fields were defined but are not used for the purposes of this assignment. Removed to same memory.
        /* _package = dataarray[1];
        _kind = Types.Kind.valueOf(dataarray[2]);
        _area = dataarray[4];
        _details = dataarray[5];
        _locations = dataarray[6];
        _depTarget = dataarray[7];
        if (dataarray[10].equals("true")){
            _toSelf = true;
        } else if (dataarray[10].equals("false")) {
            _toSelf = false;
        } else {
            throw new DependenciesException("Invalid value for toself ("+dataarray[10]+") - can only be TRUE or FALSE");
        }*/
    }
    public String getTarget(boolean noSuffix) { // nosuffix option will remove the * or ? at the end of the target name
        if (noSuffix) {
            return _target.replaceAll("[*?]", "");
        } else {
            return _target;
        }
    }
    public boolean isCategory(String category) { // checks category (case insensitive)
        return _category.toLowerCase().contains(category.toLowerCase());
    }
}