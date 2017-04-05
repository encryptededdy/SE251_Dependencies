package softeng251.dependencies;

/**
 * Created by Edward Zhang on 31/03/2017.
 * Stores dependency information for a Module
 */

public class Dependency {
    private String _target;
    private String _package;
    private Types.Kind _kind;
    private String _category;
    private String _area;
    private String _details;
    private String _locations;
    private String _depTarget; // The second target in the CSV
    // Visibility and Inheritance is not needed for this assignment
    private boolean _toSelf;

    public Dependency(String[] dataarray) {
        _target = dataarray[0];
        _package = dataarray[1];
        _kind = Types.Kind.valueOf(dataarray[2]);
        _category = dataarray[3];
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
        }
    }
    public String getTarget(boolean noSuffix) {
        if (noSuffix) {
            return _target.replaceAll("[*?]", "");
        } else {
            return _target;
        }
    }
    public boolean isCategory(String category) {
        return _category.toLowerCase().contains(category.toLowerCase());
    }
}